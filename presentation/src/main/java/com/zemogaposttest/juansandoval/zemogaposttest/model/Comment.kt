package com.zemogaposttest.juansandoval.zemogaposttest.model

import com.zemogapost.juansandoval.domain.model.Comment
import javax.inject.Inject


data class CommentItem(val postId: String, val id: String, val name: String, val email: String, val body: String)

class CommentItemMapper @Inject constructor() {

    fun mapToPresentation(commentsList: List<Comment>): List<CommentItem> = commentsList.map { CommentItem(it.postId, it.id, it.name, it.email, it.body) }
}