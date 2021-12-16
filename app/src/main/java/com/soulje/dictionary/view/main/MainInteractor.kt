package com.soulje.dictionary.view.main

import com.soulje.core.AppState
import com.soulje.model.DataModel
import com.soulje.core.Interactor
import com.soulje.repository.repos.Repository
import com.soulje.repository.repos.RepositoryLocal

class MainInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : com.soulje.core.Interactor<com.soulje.core.AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.soulje.core.AppState {
        val appState: com.soulje.core.AppState
        if (fromRemoteSource) {
            appState = com.soulje.core.AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = com.soulje.core.AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }

    override suspend fun getDataByWord(word: String): com.soulje.core.AppState {
        return com.soulje.core.AppState.SearchSuccess(repositoryLocal.getDataByWord(word))
    }
}