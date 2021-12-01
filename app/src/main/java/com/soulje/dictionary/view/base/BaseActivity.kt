package com.soulje.dictionary.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.viewModel.BaseViewModel

abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>

    abstract fun renderData(appState: T)

}