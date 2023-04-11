package io.craigmiller160.videomanagerconverter.service

import io.craigmiller160.videomanagerconverter.domain.repository.FileToConvertRepository
import io.craigmiller160.videomanagerconverter.events.NewConversionEvent
import jakarta.annotation.PostConstruct
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
    @EventListener
    @Transactional
    fun startup(event: ContextStartedEvent) {
        fileToConvertRepository.resetInProgressToPending()
        fileToConvertRepository.flush()
        val pendingFiles = fileToConvertRepository.findAllPending()
    }

    @EventListener
    fun newConversion(event: NewConversionEvent) {
        TODO("New conversion")
    }
}