package com.soulje.dictionary.view.main

import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.repository.Repository
import com.soulje.dictionary.presenter.Interactor
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}