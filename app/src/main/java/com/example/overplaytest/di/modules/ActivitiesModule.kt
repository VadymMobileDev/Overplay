package com.example.overplaytest.di.modules

import com.example.overplaytest.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesModule {


    @ContributesAndroidInjector
    fun mainActivityInjector(): MainActivity
}