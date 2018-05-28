package com.zemogapost.juansandoval.domain.model.repository

import com.zemogapost.juansandoval.domain.model.Comment
import io.reactivex.Single


interface CommentRepository {

    val key: String

    fun get(postId: String, refresh: Boolean): Single<List<Comment>>
}
