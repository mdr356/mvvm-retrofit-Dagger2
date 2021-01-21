package com.amiiboapi.android.myamiibo.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.viewmodel.AmiiboDetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailsActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var amiiboDetailsViewModel: AmiiboDetailsViewModel

    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        amiiboDetailsViewModel.getAmiiboDetails("123")?.observe(this, {
            progressBar.visibility = View.GONE
            Log.d("MainActivity", it.toString())
        })
    }
}