package com.soulje.repository.repos

interface Repository<T> {
    suspend fun getData(word: String): T
}