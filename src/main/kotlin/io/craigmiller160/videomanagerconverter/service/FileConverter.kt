package io.craigmiller160.videomanagerconverter.service

interface FileConverter {
    fun convert(): Result<Unit>
}