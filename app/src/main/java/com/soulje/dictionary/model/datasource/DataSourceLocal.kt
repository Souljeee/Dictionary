package com.soulje.dictionary.model.datasource

import com.soulje.dictionary.model.data.DataModel
import io.reactivex.Observable

class DataSourceLocal(private val localProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = localProvider.getData(word)
}