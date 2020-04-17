package ru.flowernetes.script.data.mapper

import com.mongodb.client.gridfs.model.GridFSFile
import org.springframework.stereotype.Component
import ru.flowernetes.entity.script.SourceScript
import ru.flowernetes.script.api.domain.entity.NoSourceScriptMetadataException
import ru.flowernetes.script.api.domain.entity.ScriptMetadataKeys
import ru.flowernetes.util.mapper.Mapper
import java.time.ZoneId

@Component
class GridFSFileToSourceScriptMapper : Mapper<GridFSFile, SourceScript> {
    override fun map(it: GridFSFile): SourceScript {
        val metadata = it.metadata ?: throw NoSourceScriptMetadataException(it.id.toString())
        return SourceScript(
          it.objectId.toString(),
          metadata.getString(ScriptMetadataKeys.NAME.name),
          it.filename,
          metadata.getString(ScriptMetadataKeys.TAG.name),
          metadata.getString(ScriptMetadataKeys.RUN_FILE_PATH.name),
          it.uploadDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
          metadata.getLong(ScriptMetadataKeys.TEAM_ID.name),
          metadata.getBoolean(ScriptMetadataKeys.IS_PUBLIC.name)
        )
    }
}