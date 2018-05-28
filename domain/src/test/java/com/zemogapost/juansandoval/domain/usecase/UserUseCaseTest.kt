package com.zemogapost.juansandoval.domain.usecase

import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.domain.createUser
import com.zemogapost.juansandoval.domain.model.repository.UserRepository
import com.zemogapost.juansandoval.domain.model.usecase.UserUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class UserUseCaseTest {

    private lateinit var usecase: UserUseCase

    private val mockRepository = mock<UserRepository> {}

    private val userId = "1"
    private val user = createUser()

    @Before
    fun setUp() {
        usecase = UserUseCase(mockRepository)
    }

    @Test
    fun `repository get success`() {
        // given
        Mockito.`when`(mockRepository.get(userId, false)).thenReturn(Single.just(user))

        // when
        val test = usecase.get(userId, false).test()

        // then
        Mockito.verify(mockRepository).get(userId, false)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(user)
    }
}
