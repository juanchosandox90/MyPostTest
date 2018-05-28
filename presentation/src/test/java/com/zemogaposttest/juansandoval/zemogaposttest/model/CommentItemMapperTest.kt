package com.zemogaposttest.juansandoval.zemogaposttest.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class CommentItemMapperTest {

    private lateinit var mapper: CommentItemMapper

    @Before
    fun setUp() {
        mapper = CommentItemMapper()
    }

    @Test
    fun `map domain to presentation`() {
        // given
        val comment = com.zemogaposttest.juansandoval.zemogaposttest.createComment()

        // when
        val commentItem = mapper.mapToPresentation(listOf(comment))[0]

        // then
        Assert.assertTrue(commentItem.postId == comment.postId)
        Assert.assertTrue(commentItem.id == comment.id)
        Assert.assertTrue(commentItem.name == comment.name)
        Assert.assertTrue(commentItem.email == comment.email)
        Assert.assertTrue(commentItem.body == comment.body)
    }
}