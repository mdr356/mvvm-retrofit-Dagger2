package com.amiiboapi.android.myamiibo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amiiboapi.android.myamiibo.model.Amiibo
import com.amiiboapi.android.myamiibo.repository.AmiiboRepository
import javax.inject.Inject

class AmiiboViewModel @Inject constructor(val amiiboRepository: AmiiboRepository): ViewModel() {

    private var amiiboLiveData : MutableLiveData<Amiibo>? = null

    fun getAmiibo() : LiveData<Amiibo>? {
        amiiboLiveData = amiiboRepository.getAmiiboList()
        return amiiboLiveData
    }
}
