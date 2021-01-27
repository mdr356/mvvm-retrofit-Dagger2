package com.amiiboapi.android.myamiibo.view

import android.R.id.message
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.amiiboapi.android.myamiibo.BaseActivity
import com.amiiboapi.android.myamiibo.R
import com.amiiboapi.android.myamiibo.model.AmiiboData
import com.amiiboapi.android.myamiibo.view.MainActivity.Companion.RELOAD_AMOBII
import com.amiiboapi.android.myamiibo.viewmodel.AmiiboDetailsViewModel
import com.amiiboapi.android.myamiibo.viewmodel.HidePurchaseButton
import com.amiiboapi.android.myamiibo.viewmodel.ShowErrorView
import com.amiiboapi.android.myamiibo.viewmodel.UpdateView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailsActivity : BaseActivity(){

    companion object {
        val AMIIBO_DATABASE_LOCAL_ID =  "amiibo_local_id"
    }

    @Inject
    lateinit var amiiboDetailsViewModel: AmiiboDetailsViewModel

    private var amiiboDataId: Int? = null

    private lateinit var amiiboImageView: ImageView
    private lateinit var amiiboSeries : TextView
    private lateinit var amiiboCharacter: TextView
    private lateinit var amiiboGameSeries: TextView
    private lateinit var amiiboCharacterName: TextView
    private lateinit var amiiboCharacterType: TextView
    private lateinit var amiiboCharacterRelease: TextView
    private lateinit var amiiboDeleteBtn: Button
    private lateinit var purchaseAmiiboBtn: Button
    private lateinit var imageLayout: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        amiiboDataId = intent.getIntExtra(AMIIBO_DATABASE_LOCAL_ID, -1)
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
        amiiboDeleteBtn = findViewById(R.id.amiibo_delete_btn)
        purchaseAmiiboBtn = findViewById(R.id.purchase_amiibo_btn)
        imageLayout = findViewById(R.id.imageLayout)

        amiiboDeleteBtn.setOnClickListener {
            amiiboDataId?.let { it1 -> amiiboDetailsViewModel.deleteAmiiboData(it1) }

            setResult(RELOAD_AMOBII, Intent())
            finish()
        }

        purchaseAmiiboBtn.setOnClickListener {
            setPurchaseBackGround()
            amiiboDataId?.let { it1 -> amiiboDetailsViewModel.updatePurchase(it1) }
        }
    }

    override fun initApiCall() {
        amiiboDataId?.let {
            amiiboDetailsViewModel.getAmiiboDetails(
                it
            ).observe(this, {
                when (it) {
                    ShowErrorView -> showErrorLayout()
                    is UpdateView -> populateView(it.amiiboData)
                    is HidePurchaseButton -> purchaseAmiiboBtn.visibility = View.GONE
                }
            })
        }

        amiiboDataId?.let { amiiboDetailsViewModel.getAmiiboDetails(it) }
    }

    override fun purchaseFilter() {
        //no op
    }

    private fun populateView(amiiboData: AmiiboData) {
        amiiboSeries.text = amiiboData.amiiboSeries
        amiiboCharacter.text = amiiboData.character
        amiiboGameSeries.text = amiiboData.gameSeries
        amiiboCharacterName.text = amiiboData.name
        amiiboCharacterType.text = amiiboData.type
        amiiboCharacterRelease.text = amiiboData.release.toString()
        if(amiiboData.purchase == 1) setPurchaseBackGround()

        Picasso.get().load(amiiboData.image).into(amiiboImageView, object : Callback {
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

    fun setPurchaseBackGround() {
        imageLayout.setBackgroundColor(Color.parseColor("#008000"))
    }
}
