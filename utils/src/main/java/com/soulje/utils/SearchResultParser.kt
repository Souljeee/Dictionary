package com.soulje.dictionary.utils

import com.soulje.core.AppState

import com.soulje.model.DataModel
import com.soulje.model.Meanings
import com.soulje.model.Translation
import com.soulje.repository.db.HistoryEntity



fun parseLocalSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, false))
}

private fun mapResult(
    appState: AppState,
    isOnline: Boolean
): List<com.soulje.model.DataModel> {
    val newSearchResults = arrayListOf<com.soulje.model.DataModel>()
    when (appState) {
        is AppState.Success -> {
            getSuccessResultData(appState, isOnline, newSearchResults)
        }
    }
    return newSearchResults
}

private fun getSuccessResultData(
    appState: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<com.soulje.model.DataModel>
) {
    val dataModels: List<com.soulje.model.DataModel> = appState.data as List<com.soulje.model.DataModel>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(com.soulje.model.DataModel(searchResult.text, arrayListOf()))
            }
        }
    }
}

private fun parseOnlineResult(dataModel: com.soulje.model.DataModel, newDataModels: ArrayList<com.soulje.model.DataModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<com.soulje.model.Meanings>()
        for (meaning in dataModel.meanings!!) {
            if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                newMeanings.add(com.soulje.model.Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(com.soulje.model.DataModel(dataModel.text, newMeanings))
        }
    }
}