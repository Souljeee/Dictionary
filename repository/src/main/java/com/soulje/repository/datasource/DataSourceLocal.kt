package com.soulje.repository.datasource


import com.soulje.core.AppState
import com.soulje.model.DataModel

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getDataByWord(word:String): com.soulje.model.DataModel
}