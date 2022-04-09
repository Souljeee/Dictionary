package com.soulje.core

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.soulje.core.databinding.LoadingLayoutBinding
import com.soulje.model.DataModel


abstract class BaseActivity<T : AppState> : AppCompatActivity() {

    private lateinit var binding: LoadingLayoutBinding
    abstract val model: com.soulje.core.BaseViewModel<T>


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