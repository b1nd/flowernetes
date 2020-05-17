package ru.flowernetes.entity.task

enum class TaskStatus {
    INACTIVE,
    WAITING,
    PENDING,
    RUNNING,
    SUCCESS,
    ERROR,
    KILLED,
    QUOTA_EXCEEDED,
    TIME_EXCEEDED
}