package com.amiiboapi.android.myamiibo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {

    private lateinit var progressBar : ProgressBar
    private lateinit var errorLayout : LinearLayout
    private lateinit var purchaseBtnFilter: Button


   override fun setContentView(layoutResID: Int) {
       val coordinatorLayout: CoordinatorLayout = layoutInflater.inflate(R.layout.activity_base, null) as CoordinatorLayout
       val activityContainer: FrameLayout = coordinatorLayout.findViewById(R.id.layout_container)

       progressBar = coordinatorLayout.findViewById(R.id.progressBar)
       errorLayout = coordinatorLayout.findViewById(R.id.error_layout)
       purchaseBtnFilter = coordinatorLayout.findViewById(R.id.purchaseBtnFilter)

       purchaseBtnFilter.setOnClickListener {
           purchaseFilter()
       }

       if(layoutResID == R.layout.activity_details) purchaseBtnFilter.visibility = View.GONE
       layoutInflater.inflate(layoutResID, activityContainer, true)

       super.setContentView(coordinatorLayout)
   }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun dismissProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun showErrorLayout() {
        errorLayout.visibility = View.VISIBLE
    }

    fun dismissErrorLayout() {
        errorLayout.visibility = View.GONE
    }

    abstract fun initView()
    abstract fun initApiCall()
    abstract fun purchaseFilter()
}