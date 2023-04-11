package io.craigmiller160.videomanagerconverter.domain.repository

import io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus
import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
interface FileToConvertRepository : JpaRepository<FileToConvert, UUID> {
    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun deleteAllByStatusNotIn(keepStatuses: List<ConvertStatus>)

    @Query("""
        SELECT f
        FROM FileToConvert f
        WHERE f.status = io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus.PENDING
    """)
    fun findAllPending(): List<FileToConvert>
}