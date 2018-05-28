package com.zemogaposttest.juansandoval.zemogaposttest.userdetails

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.domain.model.usecase.UserUseCase
import com.zemogaposttest.juansandoval.zemogaposttest.RxSchedulersOverrideRule
import com.zemogaposttest.juansandoval.zemogaposttest.createUser
import com.zemogaposttest.juansandoval.zemogaposttest.model.AddressItemMapper
import com.zemogaposttest.juansandoval.zemogaposttest.model.CompanyItemMapper
import com.zemogaposttest.juansandoval.zemogaposttest.model.LatLngMapper
import com.zemogaposttest.juansandoval.zemogaposttest.model.UserItemMapper
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito


class UserDetailsViewModelTest {

    private lateinit var viewModel: UserDetailsViewModel

    private val mockUseCase = mock<UserUseCase> {}
    private val mapper = UserItemMapper(addressItemMapper = AddressItemMapper(LatLngMapper()), companyItemMapper = CompanyItemMapper())

    private val userId = "1"
    private val user = createUser()

    @Rule
    @JvmField
    val rxSchedulersOverrideRule: RxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = UserDetailsViewModel(mockUseCase, mapper)
    }

    @Test
    fun `get user details succeeds`() {
        // given
        Mockito.`when`(mockUseCase.get(userId, false)).thenReturn(Single.just(user))

        // when
        viewModel.userId = userId

        // then
        Assert.assertEquals(mapper.mapToPresentation(user), viewModel.user.value)
    }
}