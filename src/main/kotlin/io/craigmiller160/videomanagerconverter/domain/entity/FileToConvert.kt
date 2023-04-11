package io.craigmiller160.videomanagerconverter.domain.entity

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.hibernate.annotations.Type
import java.util.UUID

@Entity
@Table(name = "files_to_convert")
class FileToConvert {
    @Id
    var id: UUID = UUID.randomUUID()
    var sourceFile: String = ""
    var targetFile: String = ""
    var errorMessage: String? = null
    @Version
    var version: Int = 1

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "convert_status")
    @Type(PostgreSQLEnumType::class)
    var status: ConvertStatus = ConvertStatus.PENDING
}