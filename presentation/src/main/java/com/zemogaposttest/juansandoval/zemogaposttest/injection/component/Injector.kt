package com.zemogaposttest.juansandoval.zemogaposttest.injection.component

import com.zemogaposttest.juansandoval.zemogaposttest.injection.module.AppModule
import com.zemogaposttest.juansandoval.zemogaposttest.injection.module.NetworkModule
import com.zemogaposttest.juansandoval.zemogaposttest.injection.module.RepositoryModule
import com.zemogaposttest.juansandoval.zemogaposttest.injection.module.ViewModelModule
import com.zemogaposttest.juansandoval.zemogaposttest.postdetails.PostDetailsActivity
import com.zemogaposttest.juansandoval.zemogaposttest.postlist.PostListActivity
import com.zemogaposttest.juansandoval.zemogaposttest.userdetails.UserDetailsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class), (RepositoryModule::class), (ViewModelModule::class)])
interface Injector {
    fun inject(activity: PostListActivity)
    fun inject(activity: UserDetailsActivity)
    fun inject(activity: PostDetailsActivity)
}