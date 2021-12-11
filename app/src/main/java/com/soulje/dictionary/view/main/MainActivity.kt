package com.soulje.dictionary.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soulje.dictionary.R
import com.soulje.dictionary.SearchDialogFragment
import com.soulje.dictionary.databinding.ActivityMainBinding
import com.soulje.dictionary.model.data.AppState
import com.soulje.dictionary.model.data.DataModel
import com.soulje.dictionary.view.base.BaseActivity
import com.soulje.dictionary.view.details.DetailsActivity
import com.soulje.dictionary.view.history.HistoryActivity
import com.soulje.dictionary.view.main.adapter.MainAdapter
import com.soulje.dictionary.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityMainBinding

    override val model: MainViewModel by viewModel(named("main"))

    private lateinit var answer : String


    private lateinit var adapter: MainAdapter
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                openDetails(data)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model.subscribe().observe(this@MainActivity, Observer<AppState> { renderData(it) })
        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(object :
                SearchDialogFragment.OnSearchClickListener {
                override fun onClick(searchWord: String) {
                    model.getData(searchWord, true)
                }
            })
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        Log.d("tag", "пусто")
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
            }
            is AppState.Error -> {
                showViewWorking()
            }
            is AppState.SearchSuccess ->{
                appState.data?.let {
                    openDetails(it)
                }            }
        }
    }

    private fun openDetails(data: DataModel) {
        val intent = Intent(this@MainActivity,DetailsActivity::class.java)
            .putExtra("IMAGE_URL",data.meanings!![0].imageUrl)
            .putExtra("TEXT",data.text)
            .putExtra("TRANSLATION", data.meanings[0].translation!!.translation)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.menu_search -> {
                val dlgFragment : DialogFragment = SearchDataBaseDialog()
                dlgFragment.show(supportFragmentManager,"tag")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onDialogResult(resultDialog: String) {
        answer = resultDialog
        model.getDataByWord(answer)
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }

    override fun setDataToAdapter(data: List<DataModel>) = with(binding) {
        adapter = MainAdapter(onListItemClickListener,data)
        adapter.setData(data)
        mainActivityRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        mainActivityRecyclerview.adapter = adapter
        mainActivityRecyclerview.setHasFixedSize(true)

    }

}