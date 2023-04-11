package io.craigmiller160.videomanagerconverter.web.controller

import io.craigmiller160.videomanagerconverter.web.types.FileConversionRequest
import io.craigmiller160.videomanagerconverter.web.types.FileConversionResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/video-converter")
class VideoConverterController {
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun newConversion(@RequestBody request: FileConversionRequest) {
        TODO()
    }


    @GetMapping
    fun getConversionProgress(): List<FileConversionResponse> {
        TODO()
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun clearPastConversions() {
        TODO()
    }
}