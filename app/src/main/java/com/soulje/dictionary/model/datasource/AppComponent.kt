package com.soulje.dictionary.model.datasource

import android.app.Application
import com.soulje.dictionary.app.TranslatorApp
import com.soulje.dictionary.model.di.ActivityModule
import com.soulje.dictionary.model.di.InteractorModule
import com.soulje.dictionary.model.di.RepositoryModule
import com.soulje.dictionary.model.di.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        AndroidSupportInjectionModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


    fun inject(englishVocabularyApp: TranslatorApp)
}