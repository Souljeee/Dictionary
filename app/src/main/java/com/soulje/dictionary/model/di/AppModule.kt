package com.soulje.dictionary.model.di

import androidx.room.Room
import com.soulje.dictionary.view.main.MainActivity

import com.soulje.dictionary.view.main.MainInteractor
import com.soulje.dictionary.viewModel.MainViewModel
import com.soulje.historyscreen.HistoryActivity
import com.soulje.historyscreen.HistoryInteractor
import com.soulje.model.DataModel
import com.soulje.repository.datasource.RetrofitImplementation
import com.soulje.repository.datasource.RoomDataBaseImplementation
import com.soulje.repository.repos.Repository
import com.soulje.repository.repos.RepositoryImplementation
import com.soulje.repository.repos.RepositoryImplementationLocal
import com.soulje.repository.repos.RepositoryLocal
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { Room.databaseBuilder(androidContext(), com.soulje.repository.db.HistoryDataBase::class.java, "HistoryDB").fallbackToDestructiveMigration().build() }
    single { get<com.soulje.repository.db.HistoryDataBase>().historyDao() }

    single<Repository<List<DataModel>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }

    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(
                get()
            )
        )
    }

    scope(named("MAIN_SCOPE")) {
        scoped { MainInteractor(get(), get()) }
        viewModel { MainViewModel(get()) }
    }

    scope(named("HISTORY_SCOPE")) {
        scoped { HistoryInteractor(get(), get()) }
        viewModel { com.soulje.historyscreen.HistoryViewModel(get()) }
    }



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

    viewModel(named("history")) { com.soulje.historyscreen.HistoryViewModel(get(named("history"))) }
}