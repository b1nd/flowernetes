package ru.flowernetes.entity.workload

data class Interval<T>(
  val from: T,
  val to: T
)

fun <T, R> Interval<T>.map(f: (T) -> R): Interval<R> = Interval(
  from = f(from),
  to = f(to)
)