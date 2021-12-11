package com.soulje.dictionary.view

import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import io.reactivex.Observable

interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
    suspend fun getDataByWord(word: String): AppState
}