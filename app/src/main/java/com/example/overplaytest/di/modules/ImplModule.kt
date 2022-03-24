package com.example.overplaytest.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.overplaytest.di.impl.ViewModelFactoryImpl
import dagger.Binds
import dagger.Module

@Module
interface ImplModule {
    @Binds fun bindViewModelFactory(factory: ViewModelFactoryImpl): ViewModelProvider.Factory
}
