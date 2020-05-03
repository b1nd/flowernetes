package ru.flowernetes.workload.domain.usecase

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.entity.file.FileDto
import ru.flowernetes.workload.api.domain.entity.LogMetadataKeys
import ru.flowernetes.workload.api.domain.entity.NoSuchWorkloadLogException
import ru.flowernetes.workload.api.domain.usecase.GetWorkloadLogFileDtoByWorkloadIdUseCase

@Component
class GetWorkloadLogFileDtoByWorkloadIdUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate
) : GetWorkloadLogFileDtoByWorkloadIdUseCase {

    override fun exec(workloadId: Long): FileDto {
        val logCriteria = Criteria.where(LogMetadataKeys.LOG.key).exists(true)
          .andOperator(Criteria.where(LogMetadataKeys.WORKLOAD_ID.key).`is`(workloadId))
        val gridFSFile = gridFsTemplate.findOne(Query(logCriteria))
          ?: throw NoSuchWorkloadLogException(workloadId)
        val gridFsResource = gridFsTemplate.getResource(gridFSFile)

        return FileDto(
          gridFsResource.filename,
          gridFsResource.contentType,
          gridFsResource.inputStream
        )
    }
}