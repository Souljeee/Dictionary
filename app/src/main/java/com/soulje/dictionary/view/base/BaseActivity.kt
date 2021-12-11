package com.soulje.dictionary.view.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.soulje.dictionary.R
import com.soulje.dictionary.databinding.LoadingLayoutBinding
import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.view.Interactor
import com.soulje.dictionary.viewModel.BaseViewModel


abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    private lateinit var binding: LoadingLayoutBinding
    abstract val model: BaseViewModel<T>


    override fun onResume() {
        super.onResume()
        binding = LoadingLayoutBinding.inflate(layoutInflater)

    }



    protected fun showViewWorking() {
        binding.progressBar.visibility = View.GONE
    }

    protected fun showViewLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }


    abstract fun setDataToAdapter(data: List<DataModel>)
}