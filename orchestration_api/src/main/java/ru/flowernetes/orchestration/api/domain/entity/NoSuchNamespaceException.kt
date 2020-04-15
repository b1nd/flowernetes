package ru.flowernetes.orchestration.api.domain.entity

class NoSuchNamespaceException(namespace: String) : NoSuchElementException("There is no namespace: $namespace")