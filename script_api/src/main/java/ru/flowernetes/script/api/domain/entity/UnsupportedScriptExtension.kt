package ru.flowernetes.script.api.domain.entity

import ru.flowernetes.entity.script.SourceScript

class UnsupportedScriptExtension(sourceScript: SourceScript) :
  UnsupportedOperationException("Script $sourceScript has unsupported runFilePath extension")