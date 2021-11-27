package com.soulje.dictionary.view.base

import com.soulje.dictionary.model.data.AppState

interface IView {
    fun renderData(appState: AppState)
}