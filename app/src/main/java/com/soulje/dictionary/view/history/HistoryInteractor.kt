package com.soulje.dictionary.view.history

import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.repository.Repository
import com.soulje.dictionary.model.repository.RepositoryLocal
import com.soulje.dictionary.view.Interactor

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState{
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }

    override suspend fun getDataByWord(word: String): AppState {
        TODO("Not yet implemented")
    }
}