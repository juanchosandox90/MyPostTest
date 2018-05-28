package com.zemogaposttest.juansandoval.zemogaposttest.postdetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zemogaposttest.juansandoval.zemogaposttest.Data
import com.zemogaposttest.juansandoval.zemogaposttest.DataState
import com.zemogaposttest.juansandoval.zemogaposttest.model.CommentItem
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItem
import com.zemogapost.juansandoval.domain.model.usecase.UserPostUseCase
import com.zemogapost.juansandoval.domain.model.usecase.CommentsUseCase
import com.zemogaposttest.juansandoval.zemogaposttest.model.CommentItemMapper
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItemMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


data class UserIdPostId(val userId: String, val postId: String)

class PostDetailsViewModel @Inject constructor(private val userPostUseCase: UserPostUseCase,
                                               private val commentsUseCase: CommentsUseCase,
                                               private val postItemMapper: PostItemMapper,
                                               private val commentItemMapper: CommentItemMapper) : ViewModel() {

    val post = MutableLiveData<PostItem>()
    val comments = MutableLiveData<Data<List<CommentItem>>>()
    private val compositeDisposable = CompositeDisposable()

    var userIdPostId: UserIdPostId? = null
        set(value) {
            if (field == null) {
                field = value
                getPost()
                getComments()
            }
        }

    fun getPost() =
            compositeDisposable.add(userPostUseCase.get(userIdPostId!!.userId, userIdPostId!!.postId, false)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map { postItemMapper.mapToPresentation(it) }
                    .subscribe({ post.postValue(it) }, { }))


    fun getComments(refresh: Boolean = false) =
            compositeDisposable.add(commentsUseCase.get(userIdPostId!!.postId, refresh)
                    .doOnSubscribe { comments.postValue(Data(dataState = DataState.LOADING, data = comments.value?.data, message = null)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .map { commentItemMapper.mapToPresentation(it) }
                    .subscribe({
                        comments.postValue(Data(dataState = DataState.SUCCESS, data = it, message = null))
                    }, { comments.postValue(Data(dataState = DataState.ERROR, data = comments.value?.data, message = it.message)) }))

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}