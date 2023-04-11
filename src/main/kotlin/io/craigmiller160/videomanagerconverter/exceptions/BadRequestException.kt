package io.craigmiller160.videomanagerconverter.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
class BadRequestException(msg: String, cause: Throwable? = null) : RuntimeException(msg, cause)