package io.craigmiller160.videomanagerconverter.web.types

import io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus

data class FileConversionResponse(
    val sourceFile: String,
    val targetFile: String,
    val status: ConvertStatus,
    val errorMessage: String? = null
)
