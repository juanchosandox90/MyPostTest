package com.zemogapost.juansandoval.data.repository

import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.data.cache.Cache
import com.zemogapost.juansandoval.data.cache.model.CommentEntity
import com.zemogapost.juansandoval.data.cache.model.CommentMapper
import com.zemogapost.juansandoval.data.cache.remote.CommentsApi
import com.zemogapost.juansandoval.data.cache.repository.CommentRepositoryImpl
import com.zemogapost.juansandoval.data.createCommentEntity
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class CommentRepositoryImplTest {

    private lateinit var repository: CommentRepositoryImpl

    private val mockApi = mock<CommentsApi> {}
    private val mockCache = mock<Cache<List<CommentEntity>>>()
    private val mapper = CommentMapper()

    private val key = "Comment List"

    private val postId = "1"

    private val cacheItem = createCommentEntity().copy(name = "cache")
    private val remoteItem = createCommentEntity().copy(name = "remote")

    private val cacheList = listOf(cacheItem)
    private val remoteList = listOf(remoteItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        repository = CommentRepositoryImpl(mockApi, mockCache, mapper)
    }

    @Test
    fun `get comments cache success`() {
        // given
        Mockito.`when`(mockCache.load(key + postId)).thenReturn(Single.just(cacheList))

        // when
        val test = repository.get(postId, false).test()

        // then
        Mockito.verify(mockCache).load(key + postId)
        test.assertValue(mapper.mapToDomain(cacheList))
    }

    @Test
    fun `get comments cache fail fallback remote succeeds`() {
        // given
        Mockito.`when`(mockCache.load(key + postId)).thenReturn(Single.error(throwable))
        Mockito.`when`(mockApi.getComments(postId)).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key + postId, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(postId, false).test()

        // then
        Mockito.verify(mockCache).load(key + postId)
        Mockito.verify(mockCache).save(key + postId, remoteList)
        Mockito.verify(mockApi).getComments(postId)
        test.assertValue(mapper.mapToDomain(remoteList))
    }

    @Test
    fun `get comments remote success`() {
        // given
        Mockito.`when`(mockApi.getComments(postId)).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key + postId, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(postId, true).test()

        // then
        Mockito.verify(mockApi).getComments(postId)
        Mockito.verify(mockCache).save(key + postId, remoteList)
        test.assertValue(mapper.mapToDomain(remoteList))
    }

    @Test
    fun `get comments remote fail`() {
        // given
        Mockito.`when`(mockApi.getComments(postId)).thenReturn(Single.error(throwable))

        // when
        val test = repository.get(postId, true).test()

        // then
        Mockito.verify(mockApi).getComments(postId)
        test.assertError(throwable)
    }
}