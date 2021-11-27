package com.soulje.dictionary.model.datasource

import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.datasource.DataSource
import io.reactivex.Observable

class RoomDataBaseImplementation : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented")
    }
}