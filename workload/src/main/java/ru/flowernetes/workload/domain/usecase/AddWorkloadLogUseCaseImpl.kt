package ru.flowernetes.workload.domain.usecase

import com.mongodb.BasicDBObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.entity.workload.WorkloadLog
import ru.flowernetes.workload.api.domain.entity.LogMetadataKeys
import ru.flowernetes.workload.api.domain.usecase.AddWorkloadLogUseCase
import java.time.LocalDateTime

@Component
class AddWorkloadLogUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate
) : AddWorkloadLogUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(workload: Workload, fileDto: FileDto): WorkloadLog {
        log.debug("Saving log ${fileDto.name} for $workload")

        val metadata = BasicDBObject(mapOf(
          LogMetadataKeys.WORKLOAD_ID.name to workload.id,
          LogMetadataKeys.FILENAME.name to fileDto.name,
          LogMetadataKeys.LOG to true
        ))
        val id = gridFsTemplate.store(
          fileDto.inputStream, fileDto.name, fileDto.contentType, metadata
        ).toString()

        fileDto.inputStream.close()

        return WorkloadLog(
          id,
          fileDto.name,
          LocalDateTime.now()
        )
    }
}