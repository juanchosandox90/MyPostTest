package com.zemogapost.juansandoval.domain.model.usecase

import com.zemogapost.juansandoval.domain.model.User
import com.zemogapost.juansandoval.domain.model.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject


class UserUseCase @Inject constructor(private val repository: UserRepository) {

    fun get(userId: String, refresh: Boolean): Single<User> = repository.get(userId, refresh)
}