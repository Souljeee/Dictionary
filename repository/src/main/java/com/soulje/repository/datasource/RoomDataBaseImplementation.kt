package com.soulje.repository.datasource

import com.soulje.core.AppState
//import com.soulje.dictionary.utils.convertDataModelSuccessToEntity
//import com.soulje.dictionary.utils.mapHistoryEntityToDataModel
//import com.soulje.dictionary.utils.mapHistoryEntityToSearchResult

import com.soulje.model.DataModel

import com.soulje.repository.db.HistoryDao
import com.soulje.repository.db.HistoryEntity

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<com.soulje.model.DataModel>> {

    override suspend fun getData(word: String): List<com.soulje.model.DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }

    override suspend fun getDataByWord(word: String) : com.soulje.model.DataModel {
        return mapHistoryEntityToDataModel(historyDao.getDataByWord(word))
    }

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

    fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<com.soulje.model.DataModel> {
        val searchResult = ArrayList<com.soulje.model.DataModel>()
        if (!list.isNullOrEmpty()) {
            for (entity in list) {
                searchResult.add(com.soulje.model.DataModel(entity.word, null))
            }
        }
        return searchResult
    }

    fun mapHistoryEntityToDataModel(entity: HistoryEntity): com.soulje.model.DataModel {
        val searchResult = com.soulje.model.DataModel(
            entity.word,
            listOf(
                com.soulje.model.Meanings(
                    com.soulje.model.Translation(translation = entity.translation),
                    imageUrl = entity.imageUrl
                )
            )
        )
        return searchResult
    }

    fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
        return when (appState) {
            is AppState.Success -> {
                val searchResult = appState.data
                if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                    null
                } else {
                    HistoryEntity(searchResult[0].text!!, null, searchResult[0].meanings!![0].translation!!.translation,searchResult[0].meanings!![0].imageUrl)
                }
            }
            else -> null
        }
    }
}