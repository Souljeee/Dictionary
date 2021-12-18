package com.soulje.dictionary.view.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.soulje.dictionary.R
import com.soulje.dictionary.SearchDialogFragment
import com.soulje.dictionary.databinding.ActivityMainBinding
import com.soulje.core.AppState
import com.soulje.model.DataModel
import com.soulje.core.BaseActivity
import com.soulje.dictionary.view.details.DetailsActivity
import com.soulje.historyscreen.HistoryActivity
import com.soulje.dictionary.view.main.adapter.MainAdapter
import com.soulje.dictionary.viewModel.MainViewModel
import com.soulje.utils.viewById
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.currentScope
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.qualifier.named

class MainActivity : com.soulje.core.BaseActivity<com.soulje.core.AppState>() {

    private lateinit var binding: ActivityMainBinding

    private val myScope  by lazy { getKoin().createScope("myScope", named("MAIN_SCOPE")) }

    override val model: MainViewModel by lazy { myScope.get() }

    private val fab by viewById<FloatingActionButton>(R.id.search_fab)

    private lateinit var answer : String


    private lateinit var adapter: MainAdapter
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: com.soulje.model.DataModel) {
                openDetails(data)
            }
        }

    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDefaultSplashScreen()
        model.subscribe().observe(this@MainActivity, Observer<com.soulje.core.AppState> { renderData(it) })
        fab.setOnClickListener {
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

    private fun setDefaultSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenHideAnimation()
        }

        setSplashScreenDuration()
    }

    @RequiresApi(31)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.width.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = 100L

            slideLeft.doOnEnd { splashScreenView.remove() }
            slideLeft.start()
        }
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    private fun renderData(appState: com.soulje.core.AppState) {
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
            is com.soulje.core.AppState.SearchSuccess ->{
                appState.data?.let {
                    openDetails(it)
                }            }
        }
    }

    private fun openDetails(data: com.soulje.model.DataModel) {
        val intent = Intent(this@MainActivity,DetailsActivity::class.java)
            .putExtra("IMAGE_URL",data.meanings!![0].imageUrl)
            .putExtra("TEXT",data.text)
            .putExtra("TRANSLATION", data.meanings!![0].translation!!.translation)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, com.soulje.historyscreen.HistoryActivity::class.java))
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

    override fun setDataToAdapter(data: List<com.soulje.model.DataModel>) = with(binding) {
        adapter = MainAdapter(onListItemClickListener,data)
        adapter.setData(data)
        mainActivityRecyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        mainActivityRecyclerview.adapter = adapter
        mainActivityRecyclerview.setHasFixedSize(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        myScope.close()
    }

}