package com.amiiboapi.android.myamiibo.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amiiboapi.android.myamiibo.BaseActivity
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.adapter.AmiiboClickListener
import com.amiiboapi.android.myamiibo.adapter.AmiiboMainAdapter
import com.amiiboapi.android.myamiibo.database.DataBaseHandler
import com.amiiboapi.android.myamiibo.model.Amiibo
import com.amiiboapi.android.myamiibo.model.AmiiboData
import com.amiiboapi.android.myamiibo.viewmodel.AmiiboViewModel
import javax.inject.Inject


class MainActivity : BaseActivity(), AmiiboClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var amiiboViewModel: AmiiboViewModel

    @Inject
    lateinit var dataBaseHandler: DataBaseHandler

    @Inject
    lateinit var amiiboMainAdapter: AmiiboMainAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        setTitle(R.string.title_amiibo)
        initApiCall()
    }

    override fun initView() {
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

        amiiboMainAdapter.setListener(this)
        recyclerView.adapter = amiiboMainAdapter
        mSwipeRefreshLayout = findViewById(R.id.recycleViewContainer)
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun initApiCall() {
        amiiboViewModel.getAmiibo()?.observe(this, {
            dismissProgressBar()
            when (it) {
                null -> showErrorLayout()
                else -> updateGridView(it)
            }
        })    }

    private fun updateGridView(data: Amiibo) {
        Log.d("MainActivity", data.toString())
        dismissErrorLayout()
        amiiboMainAdapter.setList(data.amiibo)
    }

    override fun onClickListener(amiiboData: AmiiboData) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.AMIIBO_DATABASE_LOCAL_ID, amiiboData.id)
        startActivity(intent)
    }

    override fun onRefresh() {
        showProgressBar()
        dismissErrorLayout()
        dataBaseHandler.deleteAllData()
        initApiCall()
    }
}
