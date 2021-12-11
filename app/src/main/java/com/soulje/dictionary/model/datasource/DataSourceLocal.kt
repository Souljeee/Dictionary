package com.soulje.dictionary.model.datasource

import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import io.reactivex.Observable

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

    suspend fun getDataByWord(word:String): DataModel
}