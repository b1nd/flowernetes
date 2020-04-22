package ru.flowernetes.workload.api.domain.entity

class NoSuchWorkloadException(id: Long) : NoSuchElementException("There is no workload with id: $id")