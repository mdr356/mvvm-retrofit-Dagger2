package com.amiiboapi.android.myamiibo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amiiboapi.android.myamiibo.database.DataBaseHandler
import com.amiiboapi.android.myamiibo.model.Amiibo
import com.amiiboapi.android.myamiibo.repository.AmiiboRepository
import javax.inject.Inject

class AmiiboViewModel @Inject constructor(
    val amiiboRepository: AmiiboRepository,
    val dataBaseHandler: DataBaseHandler
    ): ViewModel() {

    private var amiiboLiveData : MutableLiveData<Amiibo>? = null

    fun getAmiibo() : LiveData<Amiibo>? {
        amiiboLiveData = amiiboRepository.getAmiiboList()
        return amiiboLiveData
    }

    fun deleteDatabase() : Boolean {
        return dataBaseHandler.deleteAllData()
    }

    fun purchasedFilter(): Amiibo? {
        return dataBaseHandler.purchasedFilter()
    }
}
