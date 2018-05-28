package com.zemogaposttest.juansandoval.zemogaposttest.postlist

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zemogaposttest.juansandoval.zemogaposttest.Data
import com.zemogaposttest.juansandoval.zemogaposttest.DataState
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItem
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItemMapper
import com.zemogapost.juansandoval.domain.model.usecase.UsersPostsUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class PostListViewModel @Inject constructor(private val useCase: UsersPostsUseCase,
                                            private val mapper: PostItemMapper) : ViewModel() {

    val posts = MutableLiveData<Data<List<PostItem>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        get()
    }

    fun get(refresh: Boolean = false) =
            compositeDisposable.add(useCase.get(refresh)
                    .doOnSubscribe { posts.postValue(Data(dataState = DataState.LOADING, data = posts.value?.data, message = null)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map { mapper.mapToPresentation(it) }
                    .subscribe({
                        posts.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null))
                    }, { posts.postValue(Data(dataState = DataState.ERROR, data = posts.value?.data, message = it.message)) }))

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}