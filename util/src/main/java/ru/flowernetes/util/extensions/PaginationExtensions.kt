package ru.flowernetes.util.extensions

import ru.flowernetes.pagination.api.domain.entity.*
import org.springframework.data.domain.Page as SpringPage
import org.springframework.data.domain.PageRequest as SpringPageRequest
import org.springframework.data.domain.Sort as SpringSort
import org.springframework.data.domain.Sort.Direction as SpringDirection
import org.springframework.data.domain.Sort.Order as SpringOrder

fun PageRequest.toSpringPageRequest(): SpringPageRequest = SpringPageRequest.of(
  page,
  size,
  sort.toSpringSort()
)

fun Direction.toSpringDirection(): SpringDirection = when (this) {
    Direction.ASCENDING -> SpringDirection.ASC
    else -> SpringDirection.DESC
}

fun Sort.toSpringSort(): SpringSort = SpringSort.by(
  this.orders.map(Order::toSpringOrder)
)

fun Order.toSpringOrder(): SpringOrder = SpringOrder(
  direction.toSpringDirection(),
  property
)

fun <T> SpringPage<T>.toPage(): Page<T> = Page(
  content,
  totalElements,
  totalPages,
  number
)

fun zip(properties: Array<String>, directions: Array<Direction>): Sort = Sort(
  properties.zip(directions).map { (property, direction) ->
      Order(property, direction)
  }
)