package com.example.startwarsdemo.data.response

interface DomainMapper<T : Any> {
    fun mapToDomainModel(): T
}
