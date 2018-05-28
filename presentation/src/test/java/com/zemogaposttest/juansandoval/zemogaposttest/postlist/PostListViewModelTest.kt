package com.zemogaposttest.juansandoval.zemogaposttest.postlist

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.domain.model.usecase.CombinedUserPost
import com.zemogapost.juansandoval.domain.model.usecase.UsersPostsUseCase
import com.zemogaposttest.juansandoval.zemogaposttest.*
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItemMapper
import io.reactivex.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


class PostListViewModelTest {

    private lateinit var viewModel: PostListViewModel

    private val mockUseCase = mock<UsersPostsUseCase> {}
    private val mapper = PostItemMapper()

    private val user = createUser()
    private val post = createPost()

    private val combinedUserPosts = listOf(CombinedUserPost(user, post))
    private val throwable = Throwable()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule: RxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Test
    fun `get post item list succeeds`() {
        // given
        Mockito.`when`(mockUseCase.get(false)).thenReturn(Single.just(combinedUserPosts))

        // when
        viewModel = PostListViewModel(mockUseCase, mapper)

        // then
        Assert.assertEquals(Data(dataState = DataState.SUCCESS, data = mapper.mapToPresentation(combinedUserPosts), message = null), viewModel.posts.value)
    }

    @Test
    fun `get post item list fails`() {
        // given
        Mockito.`when`(mockUseCase.get(false)).thenReturn(Single.error(throwable))

        // when
        viewModel = PostListViewModel(mockUseCase, mapper)

        // then
        Assert.assertEquals(Data(dataState = DataState.ERROR, data = null, message = throwable.message), viewModel.posts.value)
    }
}
