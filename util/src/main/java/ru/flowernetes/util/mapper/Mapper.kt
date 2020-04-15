package ru.flowernetes.util.mapper

interface Mapper<T, R> {
    fun map(it: T): R
}