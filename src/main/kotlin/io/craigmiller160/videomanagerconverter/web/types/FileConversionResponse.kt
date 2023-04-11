package io.craigmiller160.videomanagerconverter.web.types

import io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus
import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert

data class FileConversionResponse(
    val sourceFile: String,
    val targetFile: String,
    val status: ConvertStatus,
    val errorMessage: String? = null
)

fun FileToConvert.toResponse(): FileConversionResponse =
    FileConversionResponse(
        sourceFile = sourceFile,
        targetFile = targetFile,
        status = status,
        errorMessage = errorMessage
    )
