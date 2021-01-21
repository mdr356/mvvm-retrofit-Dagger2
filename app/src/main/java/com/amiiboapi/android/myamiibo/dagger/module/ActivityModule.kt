package com.amiiboapi.android.myamiibo.dagger.module

import com.amiiboapi.android.myamiibo.view.DetailsActivity
import com.amiiboapi.android.myamiibo.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity?

    @ContributesAndroidInjector
    abstract fun bindDetailsActivity(): DetailsActivity?
}
