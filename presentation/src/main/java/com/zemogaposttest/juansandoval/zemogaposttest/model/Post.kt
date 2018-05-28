package com.zemogaposttest.juansandoval.zemogaposttest.model

import com.zemogapost.juansandoval.domain.model.usecase.CombinedUserPost
import java.io.Serializable
import javax.inject.Inject


const val POST_ID_KEY = "POST_ID_KEY"

data class PostItem(val postId: String, val userId: String, val title: String, val body: String, val name: String, val username: String, val email: String) : Serializable

class PostItemMapper @Inject constructor() {

    fun mapToPresentation(cup: CombinedUserPost): PostItem = PostItem(cup.post.id, cup.user.id, cup.post.title, cup.post.body, cup.user.name, cup.user.username, cup.user.email)

    fun mapToPresentation(combinedUserPostList: List<CombinedUserPost>): List<PostItem> = combinedUserPostList.map { mapToPresentation(it) }
}