package com.soulje.historyscreen

import androidx.lifecycle.LiveData
import com.soulje.core.AppState
import com.soulje.dictionary.utils.parseLocalSearchResults
import kotlinx.coroutines.launch

class HistoryViewModel(private val interactor: HistoryInteractor) :
    com.soulje.core.BaseViewModel<com.soulje.core.AppState>() {

    private val liveDataForViewToObserve: LiveData<com.soulje.core.AppState> = _mutableLiveData

    fun subscribe(): LiveData<com.soulje.core.AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = com.soulje.core.AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        _mutableLiveData.postValue(parseLocalSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(com.soulje.core.AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = com.soulje.core.AppState.Success(null)
        super.onCleared()
    }
}