package com.amiiboapi.android.myamiibo.retrofit

import com.amiiboapi.android.myamiibo.model.Amiibo
import com.amiiboapi.android.myamiibo.model.AmiiboDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/api/amiibo/")
    fun getAmiiboList(): Call<Amiibo>

    @GET("/api/amiibo")
    fun getAmiiboDetails(
        @Query("id") id: String
    ): Call<AmiiboDetails>

}