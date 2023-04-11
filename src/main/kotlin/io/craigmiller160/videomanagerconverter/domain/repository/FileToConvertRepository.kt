package io.craigmiller160.videomanagerconverter.domain.repository

import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FileToConvertRepository : JpaRepository<FileToConvert, UUID>