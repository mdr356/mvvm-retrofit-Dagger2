package com.amiiboapi.android.myamiibo.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amiiboapi.android.myamiibo.database.DataBaseHandler
import com.amiiboapi.android.myamiibo.retrofit.RetrofitApi
import com.amiiboapi.android.myamiibo.model.Amiibo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface AmiiboRepository {
    fun getAmiiboList() : MutableLiveData<Amiibo>
}

class AmiiboRepositoryImpl @Inject constructor (
    val retrofitApi: RetrofitApi,
    val dataBaseHandler: DataBaseHandler
    ) : AmiiboRepository {

    private val TAG = "AmiiboRepository"
    private val amiiboData: MutableLiveData<Amiibo> = MutableLiveData()

    override fun getAmiiboList(): MutableLiveData<Amiibo> {
        // read database first.
        val database = dataBaseHandler.viewAmiiboList()
        if(database != null) {
            Log.d(TAG, "read data from database")
            return MutableLiveData(database)
        }

        Log.d(TAG, "making http network call")
        // else empty make request
        val callAsync: Call<Amiibo> = retrofitApi.getAmiiboList()

        callAsync.enqueue(object : Callback<Amiibo> {
            override fun onResponse(call: Call<Amiibo>, response: Response<Amiibo>) {
                val data = response.body()
                amiiboData.value = data
                data.let { dataBaseHandler.addAmiiboData(it!!) } // add data to database

            }
            override fun onFailure(call: Call<Amiibo>, t: Throwable) {
                amiiboData.value = null
            }
        })
        return amiiboData
    }
}