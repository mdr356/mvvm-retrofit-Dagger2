package com.amiiboapi.android.myamiibo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amiiboapi.android.myamiibo.model.AmiiboDetails
import com.amiiboapi.android.myamiibo.repository.AmiiboDetailsRepository
import javax.inject.Inject

class AmiiboDetailsViewModel @Inject constructor(val amiiboDetailsRepository: AmiiboDetailsRepository): ViewModel() {

    private var amiiboDetailsLiveData : MutableLiveData<AmiiboDetails>? = null

    fun getAmiiboDetails(id: String) : LiveData<AmiiboDetails>? {
        amiiboDetailsLiveData = amiiboDetailsRepository.getAmiiboDetails(id)
        return amiiboDetailsLiveData
    }
}