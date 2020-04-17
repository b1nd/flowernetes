package ru.flowernetes.script.domain.usecase

import com.mongodb.BasicDBObject
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Component
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.script.api.domain.dto.FileDto
import ru.flowernetes.script.api.domain.dto.SourceScriptDto
import ru.flowernetes.script.api.domain.entity.ScriptMetadataKeys
import ru.flowernetes.script.api.domain.usecase.AddSourceScriptUseCase
import ru.flowernetes.team.api.domain.usecase.GetCallingUserTeamUseCase
import java.time.LocalDateTime

@Component
class AddSourceScriptUseCaseImpl(
  private val gridFsTemplate: GridFsTemplate,
  private val getCallingUserTeamUseCase: GetCallingUserTeamUseCase
) : AddSourceScriptUseCase {

    override fun exec(sourceScriptDto: SourceScriptDto, fileDto: FileDto): SourceScript {
        val callingTeamId = getCallingUserTeamUseCase.exec().id
        val metadata = BasicDBObject(mapOf(
          Pair(ScriptMetadataKeys.NAME.name, sourceScriptDto.name),
          Pair(ScriptMetadataKeys.TAG.name, sourceScriptDto.tag),
          Pair(ScriptMetadataKeys.RUN_FILE_PATH.name, sourceScriptDto.runFilePath),
          Pair(ScriptMetadataKeys.TEAM_ID.name, callingTeamId),
          Pair(ScriptMetadataKeys.IS_PUBLIC.name, sourceScriptDto.isPublic),
          Pair(ScriptMetadataKeys.SOURCE.name, true)
        ))
        val id = gridFsTemplate.store(
          fileDto.inputStream, fileDto.name, fileDto.contentType, metadata
        ).toString()

        return SourceScript(
          id,
          sourceScriptDto.name,
          fileDto.name,
          sourceScriptDto.tag,
          sourceScriptDto.runFilePath,
          LocalDateTime.now(),
          callingTeamId,
          sourceScriptDto.isPublic
        )
    }
}