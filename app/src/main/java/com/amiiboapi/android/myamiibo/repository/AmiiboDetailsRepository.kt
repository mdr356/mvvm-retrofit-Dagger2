package com.amiiboapi.android.myamiibo.repository

import androidx.lifecycle.MutableLiveData
import com.amiiboapi.android.myamiibo.retrofit.RetrofitApi
import com.amiiboapi.android.myamiibo.model.AmiiboDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface AmiiboDetailsRepository {
    fun getAmiiboDetails(id: String) : MutableLiveData<AmiiboDetails>
}

class AmiiboDetailsRepositoryImpl @Inject constructor(val retrofitApi: RetrofitApi) : AmiiboDetailsRepository {
    val amiiboDetailsData: MutableLiveData<AmiiboDetails> = MutableLiveData()

    override fun getAmiiboDetails(id: String): MutableLiveData<AmiiboDetails> {
        val callAsync: Call<AmiiboDetails> = retrofitApi.getAmiiboDetails(id)
        callAsync.enqueue(object : Callback<AmiiboDetails> {
            override fun onResponse(call: Call<AmiiboDetails>, response: Response<AmiiboDetails>) {
                val data = response.body()
                amiiboDetailsData.value = data
            }

            override fun onFailure(call: Call<AmiiboDetails>, t: Throwable) {
                amiiboDetailsData.value = null
            }

        })
        return amiiboDetailsData
    }

}
