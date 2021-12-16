package com.soulje.historyscreen

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.soulje.core.AppState
import com.soulje.core.BaseActivity

import com.soulje.historyscreen.databinding.ActivityHistoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class HistoryActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityHistoryBinding
    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    protected fun renderData(appState: com.soulje.core.AppState) {
        when (appState) {
            is com.soulje.core.AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        Log.d("tag", "пусто")
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is com.soulje.core.AppState.Loading -> {
                showViewLoading()
            }
            is com.soulje.core.AppState.Error -> {
                showViewWorking()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<com.soulje.model.DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: HistoryViewModel by viewModel(named("history"))
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, Observer<com.soulje.core.AppState> { renderData(it) })
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }
}