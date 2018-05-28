package com.zemogapost.juansandoval.domain.model.usecase

import com.zemogapost.juansandoval.domain.model.Comment
import com.zemogapost.juansandoval.domain.model.repository.CommentRepository
import io.reactivex.Single
import javax.inject.Inject


class CommentsUseCase @Inject constructor(private val repository: CommentRepository) {

    fun get(postId: String, refresh: Boolean): Single<List<Comment>> = repository.get(postId, refresh)
}
