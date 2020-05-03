package ru.flowernetes.workload.domain.usecase

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.workload.api.domain.entity.NoSuchWorkloadOutputFileException
import ru.flowernetes.workload.api.domain.entity.OutputFileMetadataKeys
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadOutputFileDtoByWorkloadIdUseCase

@Component
class GetWorkloadOutputFileDtoByWorkloadIdUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate
) : GetWorkloadOutputFileDtoByWorkloadIdUseCase {

    override fun exec(workloadId: Long): FileDto {
        val outputFileCriteria = Criteria.where(OutputFileMetadataKeys.OUTPUT.key).exists(true)
          .andOperator(Criteria.where(OutputFileMetadataKeys.WORKLOAD_ID.key).`is`(workloadId))
        val gridFSFile = gridFsTemplate.findOne(Query(outputFileCriteria))
          ?: throw NoSuchWorkloadOutputFileException(workloadId)
        val gridFsResource = gridFsTemplate.getResource(gridFSFile)

        return FileDto(
          gridFsResource.filename,
          gridFsResource.contentType,
          gridFsResource.inputStream
        )
    }
}