package com.soulje.dictionary.presenter

import com.soulje.dictionary.view.base.IView

interface Presenter<V : IView> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)
}
