package com.amiiboapi.android.myamiibo.dagger.module

import com.amiiboapi.android.myamiibo.database.DataBaseHandler
import com.amiiboapi.android.myamiibo.retrofit.RetrofitApi
import com.amiiboapi.android.myamiibo.repository.AmiiboRepository
import com.amiiboapi.android.myamiibo.repository.AmiiboRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideAmiiboRepository(
        retrofitApi : RetrofitApi,
        dataBaseHandler: DataBaseHandler
    ): AmiiboRepository = AmiiboRepositoryImpl(retrofitApi,
        dataBaseHandler)

}