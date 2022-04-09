package com.soulje.core

sealed class AppState{
    data class Success(val data: List<com.soulje.model.DataModel>?) : AppState()
    data class SearchSuccess(val data: com.soulje.model.DataModel?) : AppState()
    data class Error(val error: Throwable) : AppState()
    data class Loading(val progress: Int?) : AppState()
}
