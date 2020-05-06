package ru.flowernetes.monitoring.api.domain.entity

enum class Topic(val destination: String) {
    TOPIC("/topic"),
    WORKFLOW(TOPIC.key("workflow"));

    fun key(topicKey: Any): String {
        return "$destination/$topicKey"
    }
}