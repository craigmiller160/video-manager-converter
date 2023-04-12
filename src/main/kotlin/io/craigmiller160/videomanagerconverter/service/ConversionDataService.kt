package io.craigmiller160.videomanagerconverter.service

import io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus
import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import io.craigmiller160.videomanagerconverter.domain.repository.FileToConvertRepository
import io.craigmiller160.videomanagerconverter.events.NewConversionEvent
import io.craigmiller160.videomanagerconverter.exceptions.BadRequestException
import io.craigmiller160.videomanagerconverter.web.types.FileConversionRequest
import io.craigmiller160.videomanagerconverter.web.types.FileConversionResponse
import io.craigmiller160.videomanagerconverter.web.types.toResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConversionDataService(
    private val fileToConvertRepository: FileToConvertRepository,
    private val publisher: ApplicationEventPublisher
) {
    @Transactional
    fun createNewConversion(request: FileConversionRequest): FileConversionResponse {
        if (!request.sourceFile.endsWith("mkv")) {
            throw BadRequestException("Only .mkv files are supported at this time")
        }

        val file = FileToConvert().apply {
            sourceFile = request.sourceFile
            targetFile = request.sourceFile.replace(Regex("mkv$"), "mp4")
        }
            .let { fileToConvertRepository.save(it) }
        publisher.publishEvent(NewConversionEvent(file.id))
        return file.toResponse()
    }

    @Transactional
    fun restartFailedConversions() {
        fileToConvertRepository.resetFailedToPending()
        fileToConvertRepository.flush()
        fileToConvertRepository.findAllPending()
            .forEach { file -> publisher.publishEvent(NewConversionEvent(file.id)) }
    }

    fun getAllConversions(): List<FileConversionResponse> =
        fileToConvertRepository.findAll()
            .map { it.toResponse() }

    @Transactional
    fun clearPastConversions() =
        fileToConvertRepository.deleteAllByStatusNotIn(listOf(
            ConvertStatus.PENDING, ConvertStatus.IN_PROGRESS
        ))

    @Transactional
    fun clearAllConversions() = fileToConvertRepository.deleteAll()
}