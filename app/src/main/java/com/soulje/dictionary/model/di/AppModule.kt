package com.soulje.dictionary.model.di

import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.model.datasource.DataSource
import com.soulje.dictionary.model.datasource.RetrofitImplementation
import com.soulje.dictionary.model.datasource.RoomDataBaseImplementation
import com.soulje.dictionary.model.repository.Repository
import com.soulje.dictionary.model.repository.RepositoryImplementation
import com.soulje.dictionary.view.main.MainInteractor
import com.soulje.dictionary.viewModel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<DataSource<List<DataModel>>>(named(NAME_REMOTE)) { RetrofitImplementation() }

    single<DataSource<List<DataModel>>>(named(NAME_LOCAL)) { RoomDataBaseImplementation() }

    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImplementation(get(named(NAME_REMOTE))) }

    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(get(named(NAME_LOCAL)))
    }

    single{
        MainInteractor(
            remoteRepository = get(named(NAME_REMOTE)),
            localRepository = get(named(NAME_LOCAL))
        )
    }

    viewModel { MainViewModel(get()) }
}