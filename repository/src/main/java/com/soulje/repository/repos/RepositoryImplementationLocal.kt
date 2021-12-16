package com.soulje.repository.repos

import com.soulje.core.AppState

import com.soulje.model.DataModel

class RepositoryImplementationLocal(private val dataSource: com.soulje.repository.datasource.DataSourceLocal<List<com.soulje.model.DataModel>>) :
    RepositoryLocal<List<com.soulje.model.DataModel>> {

    override suspend fun getData(word: String): List<com.soulje.model.DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun getDataByWord(word: String): com.soulje.model.DataModel {
        return dataSource.getDataByWord(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}