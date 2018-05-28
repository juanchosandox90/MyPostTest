package com.zemogapost.juansandoval.domain.usecase

import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.domain.createComment
import com.zemogapost.juansandoval.domain.model.repository.CommentRepository
import com.zemogapost.juansandoval.domain.model.usecase.CommentsUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class CommentsUseCaseTest {

    private lateinit var usecase: CommentsUseCase

    private val mockRepository = mock<CommentRepository> {}

    private val postId = "1"
    private val commentList = listOf(createComment())

    @Before
    fun setUp() {
        usecase = CommentsUseCase(mockRepository)
    }

    @Test
    fun `repository get success`() {
        // given
        Mockito.`when`(mockRepository.get(postId, false)).thenReturn(Single.just(commentList))

        // when
        val test = usecase.get(postId, false).test()

        // then
        Mockito.verify(mockRepository).get(postId, false)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(commentList)
    }

    @Test
    fun `repository get fail`() {
        // given
        val throwable = Throwable()
        Mockito.`when`(mockRepository.get(postId, false)).thenReturn(Single.error(throwable))

        // when
        val test = usecase.get(postId, false).test()

        // then
        Mockito.verify(mockRepository).get(postId, false)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }
}