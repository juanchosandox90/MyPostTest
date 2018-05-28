package com.zemogaposttest.juansandoval.zemogaposttest.model

import com.zemogapost.juansandoval.domain.model.usecase.CombinedUserPost
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PostItemMapperTest {

    private lateinit var mapper: PostItemMapper

    @Before
    fun setUp() {
        mapper = PostItemMapper()
    }

    @Test
    fun `map domain to presentation`() {
        // given
        val user = com.zemogaposttest.juansandoval.zemogaposttest.createUser()
        val post = com.zemogaposttest.juansandoval.zemogaposttest.createPost()
        val combinedUserPost = CombinedUserPost(user, post)
        // when
        val postItem = mapper.mapToPresentation(listOf(combinedUserPost))[0]

        // then
        Assert.assertTrue(postItem.postId == post.id)
        Assert.assertTrue(postItem.userId == user.id)
        Assert.assertTrue(postItem.title == post.title)
        Assert.assertTrue(postItem.body == post.body)
        Assert.assertTrue(postItem.name == user.name)
        Assert.assertTrue(postItem.username == user.username)
        Assert.assertTrue(postItem.email == user.email)
    }
}