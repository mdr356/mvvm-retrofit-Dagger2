package com.amiiboapi.android.myamiibo.repository

import androidx.lifecycle.MutableLiveData
import com.amiiboapi.android.myamiibo.retrofit.RetrofitApi
import com.amiiboapi.android.myamiibo.model.Amiibo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface AmiiboRepository {
    fun getAmiiboList() : MutableLiveData<Amiibo>
}

class AmiiboRepositoryImpl @Inject constructor(val retrofitApi: RetrofitApi) : AmiiboRepository {
    val amiiboData: MutableLiveData<Amiibo> = MutableLiveData()

    override fun getAmiiboList(): MutableLiveData<Amiibo> {
        val callAsync: Call<Amiibo> = retrofitApi.getAmiiboList()
        callAsync.enqueue(object : Callback<Amiibo> {
            override fun onResponse(call: Call<Amiibo>, response: Response<Amiibo>) {
                val data = response.body()
                amiiboData.value = data
            }
            override fun onFailure(call: Call<Amiibo>, t: Throwable) {
                amiiboData.value = null
            }
        })
        return amiiboData
    }
}
