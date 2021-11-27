package com.soulje.dictionary.model.repository

import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.datasource.DataSource
import com.soulje.dictionary.model.repository.Repository
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}