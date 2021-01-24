package com.amiiboapi.android.myamiibo.dagger.component

import android.app.Application
import android.content.Context
import com.amiiboapi.android.myamiibo.AmiiboApplication
import com.amiiboapi.android.myamiibo.dagger.module.ActivityModule
import com.amiiboapi.android.myamiibo.dagger.module.AmiiboAdapterModule
import com.amiiboapi.android.myamiibo.dagger.module.RepositoryModule
import com.amiiboapi.android.myamiibo.dagger.module.RetrofitModule
import com.amiiboapi.android.myamiibo.dagger.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        RetrofitModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        AmiiboAdapterModule::class,
    ]
)
interface AppComponent : AndroidInjector<AmiiboApplication?> {
    override fun inject(app: AmiiboApplication?)

    @dagger.Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent?
    }
}


