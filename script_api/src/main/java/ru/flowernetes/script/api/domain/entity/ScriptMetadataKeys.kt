package ru.flowernetes.script.api.domain.entity

enum class ScriptMetadataKeys {
    NAME,
    TAG,
    SOURCE,
    RUN_FILE_PATH,
    IS_PUBLIC,
    TEAM_ID;

    val key = "metadata.$name"
}