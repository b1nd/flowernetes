package ru.flowernetes.entity.containerization

data class ImageName(
  val nameWithRepository: String,
  val tag: String
) {
    val fullName = "$nameWithRepository:$tag"
}