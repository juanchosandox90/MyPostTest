package com.zemogapost.juansandoval.domain.model.repository

import com.zemogapost.juansandoval.domain.model.User
import io.reactivex.Single


interface UserRepository {

    val key: String

    fun get(refresh: Boolean): Single<List<User>>

    fun get(userId: String, refresh: Boolean): Single<User>
}