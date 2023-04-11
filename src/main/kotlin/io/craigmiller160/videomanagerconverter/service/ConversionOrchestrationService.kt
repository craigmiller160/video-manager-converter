package io.craigmiller160.videomanagerconverter.service

import io.craigmiller160.videomanagerconverter.domain.repository.FileToConvertRepository
import org.springframework.stereotype.Service

@Service
class ConversionOrchestrationService(
    private val fileToConvertRepository: FileToConvertRepository
) {

}