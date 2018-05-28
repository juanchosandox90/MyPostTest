package com.zemogapost.juansandoval.data.repository

import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.data.cache.Cache
import com.zemogapost.juansandoval.data.cache.model.PostEntity
import com.zemogapost.juansandoval.data.cache.model.PostMapper
import com.zemogapost.juansandoval.data.cache.remote.PostsApi
import com.zemogapost.juansandoval.data.cache.repository.PostRepositoryImpl
import com.zemogapost.juansandoval.data.createPostEntity
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class PostRepositoryImplTest {

    private lateinit var repository: PostRepositoryImpl

    private val mockApi = mock<PostsApi> {}
    private val mockCache = mock<Cache<List<PostEntity>>>()
    private val mapper = PostMapper()

    private val key = "Post List"

    private val postId = "1"

    private val cacheItem = createPostEntity().copy(title = "cache")
    private val remoteItem = createPostEntity().copy(title = "remote")

    private val cacheList = listOf(cacheItem)
    private val remoteList = listOf(remoteItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        repository = PostRepositoryImpl(mockApi, mockCache, mapper)
    }

    @Test
    fun `get posts cache success`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.just(cacheList))

        // when
        val test = repository.get(false).test()

        // then
        Mockito.verify(mockCache).load(key)
        test.assertValue(mapper.mapToDomain(cacheList))
    }

    @Test
    fun `get posts cache fail fallback remote succeeds`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.error(throwable))
        Mockito.`when`(mockApi.getPosts()).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(false).test()

        // then
        Mockito.verify(mockCache).load(key)
        Mockito.verify(mockCache).save(key, remoteList)
        Mockito.verify(mockApi).getPosts()
        test.assertValue(mapper.mapToDomain(remoteList))
    }

    @Test
    fun `get post cache success`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.just(cacheList))

        // when
        val test = repository.get(postId, false).test()

        // then
        Mockito.verify(mockCache).load(key)
        test.assertValue(mapper.mapToDomain(cacheItem))
    }

    @Test
    fun `get post cache fail fallback remote succeeds`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.error(throwable), Single.just(emptyList()))
        Mockito.`when`(mockApi.getPost(postId)).thenReturn(Single.just(remoteItem))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(postId, false).test()

        // then
        Mockito.verify(mockCache, Mockito.times(2)).load(key)
        Mockito.verify(mockCache).save(key, remoteList)
        Mockito.verify(mockApi).getPost(postId)
        test.assertValue(mapper.mapToDomain(remoteItem))
    }


    @Test
    fun `get posts remote success`() {
        // given
        Mockito.`when`(mockApi.getPosts()).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(true).test()

        // then
        Mockito.verify(mockApi).getPosts()
        Mockito.verify(mockCache).save(key, remoteList)
        test.assertValue(mapper.mapToDomain(remoteList))
    }

    @Test
    fun `get posts remote fail`() {
        // given
        Mockito.`when`(mockApi.getPosts()).thenReturn(Single.error(throwable))

        // when
        val test = repository.get(true).test()

        // then
        Mockito.verify(mockApi).getPosts()
        test.assertError(throwable)
    }

    @Test
    fun `get post remote success`() {
        // given
        Mockito.`when`(mockApi.getPost(postId)).thenReturn(Single.just(remoteItem))
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(postId, true).test()

        // then
        Mockito.verify(mockApi).getPost(postId)
        test.assertValue(mapper.mapToDomain(remoteItem))
    }

    @Test
    fun `get post remote fail`() {
        // given
        Mockito.`when`(mockApi.getPost(postId)).thenReturn(Single.error(throwable))

        // when
        val test = repository.get(postId, true).test()

        // then
        Mockito.verify(mockApi).getPost(postId)
        test.assertError(throwable)
    }
}