package com.amiiboapi.android.myamiibo

import android.app.Application
import com.amiiboapi.android.myamiibo.dagger.component.AppComponent
import com.amiiboapi.android.myamiibo.dagger.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AmiiboApplication :  Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        val mComponent: AppComponent? = DaggerAppComponent.builder().application(this)?.build()
        mComponent?.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}
