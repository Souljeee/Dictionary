package com.soulje.dictionary.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soulje.dictionary.model.data.AppState
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<T : AppState>(
    protected val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
) : ViewModel() {
    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        compositeDisposable.clear()
    }
}