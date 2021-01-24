package com.amiiboapi.android.myamiibo.dagger.module

import com.amiiboapi.android.myamiibo.adapter.AmiiboMainAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class AmiiboAdapterModule {
    @Provides
    fun provideAmiiboMainAdapter() : AmiiboMainAdapter = AmiiboMainAdapter()
}