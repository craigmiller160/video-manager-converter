package io.craigmiller160.videomanagerconverter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt.auth.converter")
data class JwtAuthConverterConfig(val resourceId: String, val principalAttribute: String? = null)
