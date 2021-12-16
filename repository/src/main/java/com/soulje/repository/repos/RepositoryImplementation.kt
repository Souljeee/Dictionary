package com.soulje.repository.repos

import com.soulje.model.DataModel

class RepositoryImplementation(private val dataSource: com.soulje.repository.datasource.DataSource<List<com.soulje.model.DataModel>>) :
    Repository<List<com.soulje.model.DataModel>> {
    override suspend fun getData(word: String): List<com.soulje.model.DataModel> {
        return dataSource.getData(word)
    }
}