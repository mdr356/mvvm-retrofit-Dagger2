package com.amiiboapi.android.myamiibo.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.viewmodel.AmiiboViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var amiiboViewModel: AmiiboViewModel

    private lateinit var progressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        amiiboViewModel.getAmiibo()?.observe(this, Observer {
            progressBar.visibility = View.GONE
            Log.d("MainActivity", it.toString())
        })
    }
}
