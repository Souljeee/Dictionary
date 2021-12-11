package com.soulje.dictionary.view.main

import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.repository.Repository
import com.soulje.dictionary.model.repository.RepositoryLocal
import com.soulje.dictionary.view.Interactor
import io.reactivex.Observable

class MainInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState
        if (fromRemoteSource) {
            appState = AppState.Success(repositoryRemote.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word))
        }
        return appState
    }

    override suspend fun getDataByWord(word: String): AppState {
        return AppState.SearchSuccess(repositoryLocal.getDataByWord(word))
    }
}