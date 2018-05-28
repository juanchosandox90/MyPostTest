package com.zemogapost.juansandoval.domain.model.repository

import com.zemogapost.juansandoval.domain.model.Post
import io.reactivex.Single


interface PostRepository {

    val key: String

    fun get(refresh: Boolean): Single<List<Post>>

    fun get(postId: String, refresh: Boolean): Single<Post>
}
