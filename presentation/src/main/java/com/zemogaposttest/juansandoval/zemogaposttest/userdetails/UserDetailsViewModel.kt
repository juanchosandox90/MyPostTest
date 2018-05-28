package com.zemogaposttest.juansandoval.zemogaposttest.userdetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zemogaposttest.juansandoval.zemogaposttest.model.UserItem
import io.reactivex.disposables.CompositeDisposable
import com.zemogapost.juansandoval.domain.model.usecase.UserUseCase
import com.zemogaposttest.juansandoval.zemogaposttest.model.UserItemMapper
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class UserDetailsViewModel @Inject constructor(private val useCase: UserUseCase,
                                               private val mapper: UserItemMapper) : ViewModel() {

    val user = MutableLiveData<UserItem>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var userId: String? = null
        set(value) {
            if (field == null) {
                field = value
                get()
            }
        }

    fun get(refresh: Boolean = false) =
            compositeDisposable.add(useCase.get(userId!!, refresh)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map { mapper.mapToPresentation(it) }
                    .subscribe({ user.postValue(it) }, { }))

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}