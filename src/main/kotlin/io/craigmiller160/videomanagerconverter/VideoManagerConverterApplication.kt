package io.craigmiller160.videomanagerconverter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = ["io.craigmiller160.videomanagerconverter"])
class VideoManagerConverterApplication

fun main(args: Array<String>) {
    runApplication<VideoManagerConverterApplication>(*args)
}
