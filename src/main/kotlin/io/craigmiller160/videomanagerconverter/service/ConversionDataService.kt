package io.craigmiller160.videomanagerconverter.service

import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import io.craigmiller160.videomanagerconverter.domain.repository.FileToConvertRepository
import io.craigmiller160.videomanagerconverter.web.types.FileConversionRequest
import io.craigmiller160.videomanagerconverter.web.types.FileConversionResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConversionDataService(
    private val fileToConvertRepository: FileToConvertRepository
) {
    @Transactional
    fun createNewConversion(request: FileConversionRequest): FileConversionResponse {
        TODO()
    }

    fun getAllConversions(): List<FileConversionResponse> = TODO()

    @Transactional
    fun clearPastConversions() {
        TODO()
    }
}