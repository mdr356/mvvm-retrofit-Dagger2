package com.amiiboapi.android.myamiibo.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amiiboapi.android.myamiibo.BaseActivity
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.database.DataBaseHandler
import com.amiiboapi.android.myamiibo.model.AmiiboData
import com.amiiboapi.android.myamiibo.viewmodel.AmiiboDetailsViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailsActivity : BaseActivity(){

    companion object {
        val AMIIBO_DATABASE_LOCAL_ID =  "amiibo_local_id"
    }

    @Inject
    lateinit var amiiboDetailsViewModel: AmiiboDetailsViewModel

    @Inject
    lateinit var dataBaseHandler: DataBaseHandler

    private lateinit var amiiboImageView: ImageView
    private lateinit var amiiboSeries : TextView
    private lateinit var amiiboCharacter: TextView
    private lateinit var amiiboGameSeries: TextView
    private lateinit var amiiboCharacterName: TextView
    private lateinit var amiiboCharacterType: TextView
    private lateinit var amiiboCharacterRelease: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initView()
        initApiCall()
    }

    override fun initView() {
        showProgressBar()
        amiiboSeries = findViewById(R.id.amiibo_series)
        amiiboCharacter = findViewById(R.id.amiibo_character)
        amiiboGameSeries = findViewById(R.id.amiibo_game_series)
        amiiboCharacterName = findViewById(R.id.amiibo_character_name)
        amiiboCharacterType = findViewById(R.id.amiibo_character_type)
        amiiboCharacterRelease = findViewById(R.id.amiibo_character_release)
        amiiboImageView = findViewById(R.id.amiibo_image_view)
    }

    override fun initApiCall() {
        val data = dataBaseHandler.getAmiiboItem(intent.getIntExtra(AMIIBO_DATABASE_LOCAL_ID, -1))
        data?.let { populateView(it) }

        Picasso.get().load(data?.image).into(amiiboImageView, object: Callback {
            override fun onSuccess() {
                dismissProgressBar()
            }
            override fun onError(e: Exception?) {
                // Error loading pic
                amiiboImageView.visibility = View.GONE
                dismissProgressBar()
            }
        })
    }

    private fun populateView(amiiboData: AmiiboData) {
        amiiboSeries.text = amiiboData.amiiboSeries
        amiiboCharacter.text = amiiboData.character
        amiiboGameSeries.text = amiiboData.gameSeries
        amiiboCharacterName.text = amiiboData.name
        amiiboCharacterType.text = amiiboData.type
        amiiboCharacterRelease.text = amiiboData.release.toString()
    }
}
