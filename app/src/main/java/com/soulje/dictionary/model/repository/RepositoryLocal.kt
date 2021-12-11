package com.soulje.dictionary.model.repository

import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel

interface RepositoryLocal<T> : Repository<T> {

    suspend fun getDataByWord(word: String): DataModel

    suspend fun saveToDB(appState: AppState)
}