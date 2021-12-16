package com.soulje.repository.datasource

import com.soulje.model.DataModel

class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<com.soulje.model.DataModel>> {

    override suspend fun getData(word: String): List<com.soulje.model.DataModel> = remoteProvider.getData(word)
}