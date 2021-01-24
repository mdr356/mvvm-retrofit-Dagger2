package com.amiiboapi.android.myamiibo.dagger.module

import com.amiiboapi.android.myamiibo.retrofit.RetrofitApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @Provides
    fun getApiInterface(retroFit: Retrofit): RetrofitApi {
        return retroFit.create(RetrofitApi::class.java)
    }

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient?): Retrofit {
        return Builder()
            .baseUrl("https://www.amiiboapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .build();
    }

    @Provides
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(BODY)
            return httpLoggingInterceptor
        }
}

