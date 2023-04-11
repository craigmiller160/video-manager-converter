package io.craigmiller160.videomanagerconverter.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.craigmiller160.testcontainers.common.TestcontainersExtension
import io.craigmiller160.testcontainers.common.core.AuthenticationHelper
import io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus
import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import io.craigmiller160.videomanagerconverter.domain.repository.FileToConvertRepository
import io.craigmiller160.videomanagerconverter.web.types.FileConversionRequest
import io.craigmiller160.videomanagerconverter.web.types.FileConversionResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ExtendWith(TestcontainersExtension::class, SpringExtension::class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VideoFileControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val repo: FileToConvertRepository,
    private val objectMapper: ObjectMapper
) {
    private val authHelper = AuthenticationHelper()
    private val user = authHelper.createUser("me@gmail.com").let {
        authHelper.login(it)
    }

    @Test
    fun `starts a new conversion`() {
        val request = FileConversionRequest("/foo/bar/file.mkv")
        val response = FileConversionResponse(
            sourceFile = request.sourceFile,
            targetFile = request.sourceFile.replace("mkv", "mp4"),
            status = ConvertStatus.PENDING
        )
        mockMvc.post("/video-converter") {
            header("Authorization", "Bearer ${user.token}")
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status {
                    isOk()
                }
                content {
                    json(objectMapper.writeValueAsString(response))
                }
            }

        val results = repo.findAll()
        assertEquals(1, results.size)

        assertThat(results)
            .hasSize(1)
            .first()
            .hasFieldOrPropertyWithValue("sourceFile", "/foo/bar/file.mkv")
            .hasFieldOrPropertyWithValue("targetFile", "/foo/bar/file.mp4")
            .hasFieldOrPropertyWithValue("status", ConvertStatus.PENDING)
            .hasFieldOrPropertyWithValue("errorMessage", null)
    }

    private fun createTestFiles(): List<FileToConvert> =
        (0 until 4).map { index ->
            FileToConvert().apply {
                sourceFile = "Source-$index"
                targetFile = "Target-$index"
                status = when(index) {
                    1 -> ConvertStatus.PENDING
                    2 -> ConvertStatus.IN_PROGRESS
                    3 -> ConvertStatus.COMPLETED
                    else -> ConvertStatus.FAILED
                }
                if (index == 4) {
                    errorMessage = "The Error"
                }
            }
        }
            .let { repo.saveAll(it) }

    @Test
    fun `gets a list of all conversions`() {
        val files = createTestFiles()
        val expected = files.map { file ->
            FileConversionResponse(
                sourceFile = file.sourceFile,
                targetFile = file.targetFile,
                status = file.status,
                errorMessage = file.errorMessage
            )
        }

        mockMvc.get("/video-converter") {
            header("Authorization", "Bearer ${user.token}")
        }
            .andExpect {
                status { isOk() }
                content {
                    json(objectMapper.writeValueAsString(expected))
                }
            }
    }

    @Test
    fun `clears all past conversions`() {
        createTestFiles()

        mockMvc.delete("/video-converter") {
            header("Authorization", "Bearer ${user.token}")
        }
            .andExpect {
                status { isNoContent() }
            }

        val results = repo.findAll()
        assertThat(results).hasSize(2)
            .extracting("status")
            .contains(ConvertStatus.PENDING, ConvertStatus.IN_PROGRESS)
    }
}