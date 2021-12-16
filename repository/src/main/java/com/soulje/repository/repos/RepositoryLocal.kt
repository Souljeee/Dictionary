package com.soulje.repository.repos

import com.soulje.core.AppState
import com.soulje.model.DataModel

interface RepositoryLocal<T> : Repository<T> {

    suspend fun getDataByWord(word: String): com.soulje.model.DataModel

    suspend fun saveToDB(appState: AppState)
}