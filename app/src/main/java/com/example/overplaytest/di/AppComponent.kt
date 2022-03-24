package com.example.overplaytest.di

import android.content.Context
import com.example.overplaytest.AndroidApplication
import com.example.overplaytest.di.modules.ActivitiesModule
import com.example.overplaytest.di.modules.ImplModule
import com.example.overplaytest.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        ViewModelModule::class,
        ImplModule::class
    ]
)

interface AppComponent {

    fun inject(app: AndroidApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}
