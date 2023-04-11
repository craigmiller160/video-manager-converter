package io.craigmiller160.videomanagerconverter.controller

import io.craigmiller160.testcontainers.common.TestcontainersExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(TestcontainersExtension::class, SpringExtension::class)
@AutoConfigureMockMvc
class VideoFileControllerTest {
    @Test
    fun `starts a new conversion`() {
        TODO()
    }

    @Test
    fun `gets a list of all conversions`() {
        TODO()
    }

    @Test
    fun `clears all past conversions`() {
        TODO()
    }
}