package com.soulje.repository.datasource

interface DataSource<T> {
    suspend fun getData(word: String): T
}