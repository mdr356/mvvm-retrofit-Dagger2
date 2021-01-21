package com.amiiboapi.android.myamiibo.view

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.adapter.AmiiboMainAdapter
import com.amiiboapi.android.myamiibo.model.Amiibo
import com.amiiboapi.android.myamiibo.viewmodel.AmiiboViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var amiiboViewModel: AmiiboViewModel

    private lateinit var progressBar : ProgressBar
    private lateinit var errorScreen : LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var amiiboMainAdapter: AmiiboMainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        amiiboViewModel.getAmiibo()?.observe(this, {
            progressBar.visibility = View.GONE
            when (it) {
                null -> showErrorView()
                else -> updateGridView(it)
            }
        })
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recyclerView)
        val orientation = resources.configuration.orientation

        if(orientation == Configuration.ORIENTATION_PORTRAIT) {
            val gridLayoutManager = GridLayoutManager(this, 3)
            recyclerView.layoutManager = gridLayoutManager // set LayoutManager to RecyclerView
        }
        else {
            val gridLayoutManager = GridLayoutManager(this, 5)
            recyclerView.layoutManager = gridLayoutManager // set LayoutManager to RecyclerView
        }
        amiiboMainAdapter = AmiiboMainAdapter()
        recyclerView.adapter = amiiboMainAdapter

        progressBar = findViewById(R.id.progressBar)
        errorScreen = findViewById(R.id.error_screen)
    }

    private fun updateGridView(data: Amiibo) {
        Log.d("MainActivity", data.toString())
        errorScreen.visibility = View.GONE
        amiiboMainAdapter.setList(data.amiibo)
    }

    private fun showErrorView() {
        errorScreen.visibility = View.VISIBLE
    }
}
