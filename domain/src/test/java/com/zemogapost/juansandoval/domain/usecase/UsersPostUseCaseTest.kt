package com.zemogapost.juansandoval.domain.usecase

import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.domain.createPost
import com.zemogapost.juansandoval.domain.createUser
import com.zemogapost.juansandoval.domain.model.repository.PostRepository
import com.zemogapost.juansandoval.domain.model.repository.UserRepository
import com.zemogapost.juansandoval.domain.model.usecase.UserPostMapper
import com.zemogapost.juansandoval.domain.model.usecase.UserPostUseCase
import com.zemogapost.juansandoval.domain.model.usecase.UsersPostsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class UsersPostsUseCaseTest {

    private lateinit var usersPostsUseCase: UsersPostsUseCase

    private val mockUserRepository = mock<UserRepository> {}
    private val mockPostRepository = mock<PostRepository> {}
    private val mapper = UserPostMapper()

    private val userList = listOf(createUser())
    private val postList = listOf(createPost())

    @Before
    fun setUp() {
        usersPostsUseCase = UsersPostsUseCase(mockUserRepository, mockPostRepository, mapper)
    }

    @Test
    fun `repository get success`() {
        // given
        Mockito.`when`(mockUserRepository.get(false)).thenReturn(Single.just(userList))
        Mockito.`when`(mockPostRepository.get(false)).thenReturn(Single.just(postList))

        // when
        val test = usersPostsUseCase.get(false).test()

        // then
        Mockito.verify(mockUserRepository).get(false)
        Mockito.verify(mockPostRepository).get(false)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(mapper.map(userList, postList))
    }

    @Test
    fun `repository get fail`() {
        // given
        val throwable = Throwable()
        Mockito.`when`(mockUserRepository.get(false)).thenReturn(Single.error(throwable))
        Mockito.`when`(mockPostRepository.get(false)).thenReturn(Single.error(throwable))

        // when
        val test = usersPostsUseCase.get(false).test()

        // then
        Mockito.verify(mockUserRepository).get(false)
        Mockito.verify(mockPostRepository).get(false)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}

class UserPostUseCaseTest {

    private lateinit var userPostUseCase: UserPostUseCase

    private val mockUserRepository = mock<UserRepository> {}
    private val mockPostRepository = mock<PostRepository> {}
    private val mapper = UserPostMapper()

    private val userId = "1"
    private val postId = "1"

    private val user = createUser()
    private val post = createPost()

    @Before
    fun setUp() {
        userPostUseCase = UserPostUseCase(mockUserRepository, mockPostRepository, mapper)
    }

    @Test
    fun `repository get success`() {
        // given
        Mockito.`when`(mockUserRepository.get(userId, false)).thenReturn(Single.just(user))
        Mockito.`when`(mockPostRepository.get(postId, false)).thenReturn(Single.just(post))

        // when
        val test = userPostUseCase.get(userId, postId, false).test()

        // then
        Mockito.verify(mockUserRepository).get(userId, false)
        Mockito.verify(mockPostRepository).get(postId, false)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(mapper.map(user, post))
    }

    @Test
    fun `repository get fail`() {
        // given
        val throwable = Throwable()
        Mockito.`when`(mockUserRepository.get(userId, false)).thenReturn(Single.error(throwable))
        Mockito.`when`(mockPostRepository.get(postId, false)).thenReturn(Single.error(throwable))

        // when
        val test = userPostUseCase.get(userId, postId, false).test()

        // then
        Mockito.verify(mockUserRepository).get(userId, false)
        Mockito.verify(mockPostRepository).get(postId, false)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}
