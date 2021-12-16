package com.soulje.core


interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
    suspend fun getDataByWord(word: String): AppState
}