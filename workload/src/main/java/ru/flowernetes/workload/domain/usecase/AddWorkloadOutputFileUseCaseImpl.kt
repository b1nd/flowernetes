package ru.flowernetes.workload.domain.usecase

import com.mongodb.BasicDBObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.entity.workload.Workload
import ru.flowernetes.entity.workload.WorkloadOutputFile
import ru.flowernetes.workload.api.domain.entity.OutputFileMetadataKeys
import ru.flowernetes.workload.api.domain.usecase.AddWorkloadOutputFileUseCase
import java.time.LocalDateTime

@Component
class AddWorkloadOutputFileUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate
) : AddWorkloadOutputFileUseCase {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun exec(workload: Workload, fileDto: FileDto): WorkloadOutputFile {
        log.debug("Saving output file ${fileDto.name} for $workload")

        val metadata = BasicDBObject(mapOf(
          OutputFileMetadataKeys.WORKLOAD_ID.name to workload.id,
          OutputFileMetadataKeys.FILENAME.name to fileDto.name,
          OutputFileMetadataKeys.OUTPUT to true
        ))
        val id = gridFsTemplate.store(
          fileDto.inputStream, fileDto.name, fileDto.contentType, metadata
        ).toString()

        fileDto.inputStream.close()

        return WorkloadOutputFile(
          id,
          fileDto.name,
          LocalDateTime.now()
        )
    }
}