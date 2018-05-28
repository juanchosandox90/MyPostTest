package com.zemogaposttest.juansandoval.zemogaposttest.postdetails

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.domain.model.usecase.CombinedUserPost
import com.zemogapost.juansandoval.domain.model.usecase.CommentsUseCase
import com.zemogapost.juansandoval.domain.model.usecase.UserPostUseCase
import com.zemogaposttest.juansandoval.zemogaposttest.Data
import com.zemogaposttest.juansandoval.zemogaposttest.DataState
import com.zemogaposttest.juansandoval.zemogaposttest.RxSchedulersOverrideRule
import com.zemogaposttest.juansandoval.zemogaposttest.createComment
import com.zemogaposttest.juansandoval.zemogaposttest.model.CommentItemMapper
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItemMapper
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


class PostDetailsViewModelTest {

    private lateinit var viewModel: PostDetailsViewModel

    private val mockUserPostUseCase = mock<UserPostUseCase> {}
    private val mockCommentsUseCase = mock<CommentsUseCase> {}

    private val postItemMapper = PostItemMapper()
    private val commentItemMapper = CommentItemMapper()

    private val combinedUserPost = CombinedUserPost(com.zemogaposttest.juansandoval.zemogaposttest.createUser(), com.zemogaposttest.juansandoval.zemogaposttest.createPost())
    private val comments = listOf(createComment())

    private val userId = "1"
    private val postId = "1"

    private val throwable = Throwable()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule: RxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = PostDetailsViewModel(mockUserPostUseCase, mockCommentsUseCase, postItemMapper, commentItemMapper)
    }

    @Test
    fun `get post succeeds`() {
        // given
        Mockito.`when`(mockUserPostUseCase.get(userId, postId, false)).thenReturn(Single.just(combinedUserPost))
        Mockito.`when`(mockCommentsUseCase.get(postId, false)).thenReturn(Single.just(comments))

        // when
        viewModel.userIdPostId = UserIdPostId(userId, postId)

        // then
        Assert.assertEquals(postItemMapper.mapToPresentation(combinedUserPost), viewModel.post.value)
    }

    @Test
    fun `get comments succeeds`() {
        // given
        Mockito.`when`(mockUserPostUseCase.get(userId, postId, false)).thenReturn(Single.just(combinedUserPost))
        Mockito.`when`(mockCommentsUseCase.get(postId, false)).thenReturn(Single.just(comments))

        // when
        viewModel.userIdPostId = UserIdPostId(userId, postId)

        // then
        Assert.assertEquals(Data(dataState = DataState.SUCCESS, data = commentItemMapper.mapToPresentation(comments), message = null), viewModel.comments.value)
    }

    @Test
    fun `get comments fails`() {
        // given
        Mockito.`when`(mockUserPostUseCase.get(userId, postId, false)).thenReturn(Single.just(combinedUserPost))
        Mockito.`when`(mockCommentsUseCase.get(postId, false)).thenReturn(Single.error(throwable))

        // when
        viewModel.userIdPostId = UserIdPostId(userId, postId)

        // then
        Assert.assertEquals(Data(dataState = DataState.ERROR, data = null, message = throwable.message), viewModel.comments.value)
    }
}