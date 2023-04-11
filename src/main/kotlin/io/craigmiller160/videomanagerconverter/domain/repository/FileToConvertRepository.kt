package io.craigmiller160.videomanagerconverter.domain.repository

import io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus
import io.craigmiller160.videomanagerconverter.domain.entity.FileToConvert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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

    @Transactional
    @Query("""
        UPDATE FileToConvert f
        SET f.status = io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus.PENDING
        WHERE f.status = io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus.IN_PROGRESS
    """)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun resetInProgressToPending()

    @Transactional
    @Query("""
        UPDATE FileToConvert f    
        SET f.status = io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus.COMPLETED
        WHERE f.id = :id
    """)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun markCompleted(@Param("id") id: UUID)

    @Transactional
    @Query("""
        UPDATE FileToConvert f    
        SET f.status = io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus.IN_PROGRESS
        WHERE f.id = :id
    """)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun markInProgress(@Param("id") id: UUID)

    @Transactional
    @Query("""
        UPDATE FileToConvert f    
        SET f.status = io.craigmiller160.videomanagerconverter.domain.entity.ConvertStatus.FAILED,
        f.errorMessage = :message
        WHERE f.id = :id
    """)
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    fun markFailed(@Param("id") id: UUID, @Param("message") message: String)
}