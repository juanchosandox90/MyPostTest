package com.zemogaposttest.juansandoval.zemogaposttest.injection.module

import com.zemogaposttest.juansandoval.zemogaposttest.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApp(): App = app
}