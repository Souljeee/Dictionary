package com.soulje.historyscreen

import com.soulje.core.AppState
import com.soulje.model.DataModel

import com.soulje.core.Interactor
import com.soulje.repository.repos.Repository
import com.soulje.repository.repos.RepositoryLocal

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : com.soulje.core.Interactor<com.soulje.core.AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.soulje.core.AppState {
        return com.soulje.core.AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }

    override suspend fun getDataByWord(word: String): com.soulje.core.AppState {
        TODO("Not yet implemented")
    }
}