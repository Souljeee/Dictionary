package com.soulje.dictionary.model.datasource

import com.soulje.dictionary.db.HistoryDao
import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.datasource.DataSource
import com.soulje.dictionary.utils.convertDataModelSuccessToEntity
import com.soulje.dictionary.utils.mapHistoryEntityToDataModel
import com.soulje.dictionary.utils.mapHistoryEntityToSearchResult
import io.reactivex.Observable

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }

    override suspend fun getDataByWord(word: String) : DataModel {
        return mapHistoryEntityToDataModel(historyDao.getDataByWord(word))
    }
}