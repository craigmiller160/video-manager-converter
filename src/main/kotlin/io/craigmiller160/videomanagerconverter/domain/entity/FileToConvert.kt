package io.craigmiller160.videomanagerconverter.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import java.util.UUID

@Entity
@Table(name = "files_to_convert")
class FileToConvert {
    @Id
    var id: UUID = UUID.randomUUID()
    var sourceFile: String = ""
    var targetFile: String = ""
    var status: ConvertStatus = ConvertStatus.PENDING
    var errorMessage: String? = null
    @Version
    var version: Int = 1
}