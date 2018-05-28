package com.zemogapost.juansandoval.data.model

import com.zemogapost.juansandoval.data.cache.model.CommentEntity
import com.zemogapost.juansandoval.data.cache.model.CommentMapper
import com.zemogapost.juansandoval.data.createComment
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class CommentMapperTest {

    private lateinit var mapper: CommentMapper

    @Before
    fun setUp() {
        mapper = CommentMapper()
    }

    @Test
    fun `map entity to domain`() {
        // given
        val entity = CommentEntity("1", "2", "name", "email", "body")

        // when
        val model = mapper.mapToDomain(entity)

        // then
        Assert.assertTrue(model.postId == entity.postId)
        Assert.assertTrue(model.id == entity.id)
        Assert.assertTrue(model.name == entity.name)
        Assert.assertTrue(model.email == entity.email)
        Assert.assertTrue(model.body == entity.body)
    }

    @Test
    fun `map domain to entity`() {
        // given
        val comment = createComment()

        // when
        val entity = mapper.mapToEntity(comment)

        // then
        Assert.assertTrue(entity.postId == comment.postId)
        Assert.assertTrue(entity.id == comment.id)
        Assert.assertTrue(entity.name == comment.name)
        Assert.assertTrue(entity.email == comment.email)
        Assert.assertTrue(entity.body == comment.body)
    }
}