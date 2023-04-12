package io.craigmiller160.videomanagerconverter.service

import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FileConverterFactory(
    @Value("\${videoconverter.homeDir}")
    private val rootDir: String
) {
    fun newConverter(file: FileToConvert): FileConverter = FileConverterImpl(rootDir, file)
}