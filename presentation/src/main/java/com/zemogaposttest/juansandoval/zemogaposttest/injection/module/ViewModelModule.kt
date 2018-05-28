package com.zemogaposttest.juansandoval.zemogaposttest.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.zemogaposttest.juansandoval.zemogaposttest.ViewModelFactory
import com.zemogaposttest.juansandoval.zemogaposttest.ViewModelKey
import com.zemogaposttest.juansandoval.zemogaposttest.postdetails.PostDetailsViewModel
import com.zemogaposttest.juansandoval.zemogaposttest.postlist.PostListViewModel
import com.zemogaposttest.juansandoval.zemogaposttest.userdetails.UserDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PostListViewModel::class)
    internal abstract fun postListViewModel(viewModel: PostListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    internal abstract fun postDetailsViewModel(viewModel: PostDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    internal abstract fun userDetailsViewModel(viewModel: UserDetailsViewModel): ViewModel
}