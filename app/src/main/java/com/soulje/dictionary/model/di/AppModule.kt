package com.soulje.dictionary.model.di

import androidx.room.Room
import com.soulje.dictionary.db.HistoryDataBase
import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.datasource.DataSource
import com.soulje.dictionary.model.datasource.RetrofitImplementation
import com.soulje.dictionary.model.datasource.RoomDataBaseImplementation
import com.soulje.dictionary.model.repository.Repository
import com.soulje.dictionary.model.repository.RepositoryImplementation
import com.soulje.dictionary.model.repository.RepositoryImplementationLocal
import com.soulje.dictionary.model.repository.RepositoryLocal
import com.soulje.dictionary.view.history.HistoryInteractor
import com.soulje.dictionary.view.main.MainInteractor
import com.soulje.dictionary.viewModel.HistoryViewModel
import com.soulje.dictionary.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { Room.databaseBuilder(androidContext(), HistoryDataBase::class.java, "HistoryDB").fallbackToDestructiveMigration().build() }
    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<DataModel>>> { RepositoryImplementation(RetrofitImplementation()) }

    single<RepositoryLocal<List<DataModel>>> { RepositoryImplementationLocal(RoomDataBaseImplementation(get())) }




    single(named("main")){
        MainInteractor(
            repositoryRemote = get(),
            repositoryLocal = get()
        )
    }

    single(named("history")){
        HistoryInteractor(
            repositoryRemote = get(),
            repositoryLocal = get()
        )
    }

    viewModel(named("main")) { MainViewModel(get(named("main"))) }

    viewModel(named("history")) { HistoryViewModel(get(named("history"))) }
}