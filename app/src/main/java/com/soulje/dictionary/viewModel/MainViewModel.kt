package com.soulje.dictionary.viewModel

import androidx.lifecycle.LiveData
import com.soulje.core.AppState
import com.soulje.dictionary.view.main.MainInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val interactor: MainInteractor) :
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

    fun getDataByWord(word: String) {
        _mutableLiveData.value = com.soulje.core.AppState.Loading(null)
        viewModelCoroutineScope.launch { startInteractorByWord(word) }
    }

    private suspend fun startInteractorByWord(word:String) = withContext(Dispatchers.IO){
        _mutableLiveData.postValue(interactor.getDataByWord(word))
    }
    private suspend fun startInteractor(word: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        _mutableLiveData.postValue(interactor.getData(word, isOnline))
    }
    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(com.soulje.core.AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = com.soulje.core.AppState.Success(null)
        super.onCleared()
    }
}