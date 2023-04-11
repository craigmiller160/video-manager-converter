package io.craigmiller160.videomanagerconverter.service

import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import io.craigmiller160.videomanagerconverter.domain.repository.FileToConvertRepository
import io.craigmiller160.videomanagerconverter.events.NewConversionEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Profile("!test")
class ConversionOrchestrationService(
    private val fileToConvertRepository: FileToConvertRepository,
    private val fileConverterFactory: FileConverterFactory
) {
    private val log = LoggerFactory.getLogger(javaClass)
    @EventListener
    @Transactional
    fun startup(event: ContextStartedEvent) {
        fileToConvertRepository.resetInProgressToPending()
        fileToConvertRepository.flush()
        val pendingFiles = fileToConvertRepository.findAllPending()
        pendingFiles.forEach { file ->
            GlobalScope.launch {
                convertFile(file)
            }
        }
    }

    private suspend fun convertFile(file: FileToConvert) {
        withContext(Dispatchers.IO) {
            fileToConvertRepository.markInProgress(file.id)
        }
        val result = fileConverterFactory.newConverter(file)
            .convert()
        withContext(Dispatchers.IO) {
            result.fold({ fileToConvertRepository.markCompleted(file.id) }) { ex ->
                fileToConvertRepository.markFailed(file.id, ex.message ?: "")
                log.error("Conversion failed for file. ID: ${file.id}", ex)
            }
        }
    }

    @EventListener
    fun newConversion(event: NewConversionEvent) {
        fileToConvertRepository.findById(event.id)
            .ifPresentOrElse({ file ->
                GlobalScope.launch { convertFile(file) }
            }) {
                log.error("Unable to find file by id: {}", event.id)
            }
    }
}