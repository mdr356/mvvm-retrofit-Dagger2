package com.amiiboapi.android.myamiibo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amiiboapi.android.myamiibo.database.DataBaseHandler
import com.amiiboapi.android.myamiibo.model.AmiiboData
import javax.inject.Inject

class AmiiboDetailsViewModel @Inject constructor(val dataBaseHandler: DataBaseHandler): ViewModel() {

    var amiiboDetails : MutableLiveData<AmiiBoDetailsCommand> = MutableLiveData()

    fun getAmiiboDetails(id: Int) : MutableLiveData<AmiiBoDetailsCommand> {
        val data = dataBaseHandler.getAmiiboItem(id)
        if(data == null) amiiboDetails.postValue(ShowErrorView)
        else if (data.purchase == 1) amiiboDetails.postValue(HidePurchaseButton)

        amiiboDetails.postValue(data?.let { UpdateView(it) })
        return amiiboDetails
    }

    fun deleteAmiiboData(id: Int): Boolean {
        return dataBaseHandler.deleteAmiiboData(id)
    }

    fun updatePurchase(id: Int): Boolean {
        return dataBaseHandler.updatePurchase(id)
    }
}

sealed class AmiiBoDetailsCommand
object HidePurchaseButton: AmiiBoDetailsCommand()
data class UpdateView(val amiiboData: AmiiboData): AmiiBoDetailsCommand()
object ShowErrorView: AmiiBoDetailsCommand()