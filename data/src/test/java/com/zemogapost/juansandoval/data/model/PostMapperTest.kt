package com.zemogapost.juansandoval.data.model

import com.zemogapost.juansandoval.data.cache.model.PostEntity
import com.zemogapost.juansandoval.data.cache.model.PostMapper
import com.zemogapost.juansandoval.data.createPost
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PostMapperTest {

    private lateinit var mapper: PostMapper

    @Before
    fun setUp() {
        mapper = PostMapper()
    }

    @Test
    fun `map entity to domain`() {
        // given
        val entity = PostEntity("1", "2", "title", "body")

        // when
        val model = mapper.mapToDomain(entity)

        // then
        Assert.assertTrue(model.userId == entity.userId)
        Assert.assertTrue(model.id == entity.id)
        Assert.assertTrue(model.title == entity.title)
        Assert.assertTrue(model.body == entity.body)
    }

    @Test
    fun `map domain to entity`() {
        // given
        val post = createPost()

        // when
        val entity = mapper.mapToEntity(post)

        // then
        Assert.assertTrue(entity.userId == post.userId)
        Assert.assertTrue(entity.id == post.id)
        Assert.assertTrue(entity.title == post.title)
        Assert.assertTrue(entity.body == post.body)
    }
}