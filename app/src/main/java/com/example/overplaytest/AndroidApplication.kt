package com.example.overplaytest

import android.app.Application
import com.example.overplaytest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AndroidApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        //I Add Dagger 2 to application. Like ac an alternative we may use Koin or Kodein for realizing di.
        DaggerAppComponent.builder().context(this).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = activityInjector
}