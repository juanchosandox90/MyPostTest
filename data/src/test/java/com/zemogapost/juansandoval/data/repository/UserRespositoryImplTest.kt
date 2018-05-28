package com.zemogapost.juansandoval.data.repository

import com.nhaarman.mockito_kotlin.mock
import com.zemogapost.juansandoval.data.cache.Cache
import com.zemogapost.juansandoval.data.cache.model.*
import com.zemogapost.juansandoval.data.cache.remote.UsersApi
import com.zemogapost.juansandoval.data.cache.repository.UserRepositoryImpl
import com.zemogapost.juansandoval.data.createUserEntity
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl

    private val mockApi = mock<UsersApi> {}
    private val mockCache = mock<Cache<List<UserEntity>>>()
    private val mapper = UserMapper(addressMapper = AddressMapper(GeoMapper()), companyMapper = CompanyMapper())

    private val key = "User List"

    private val userId = "1"

    private val cacheItem = createUserEntity().copy(name = "cache")
    private val remoteItem = createUserEntity().copy(name = "remote")

    private val cacheList = listOf(cacheItem)
    private val remoteList = listOf(remoteItem)

    private val throwable = Throwable()

    @Before
    fun setUp() {
        repository = UserRepositoryImpl(mockApi, mockCache, mapper)
    }

    @Test
    fun `get users cache success`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.just(cacheList))

        // when
        val test = repository.get(false).test()

        // then
        Mockito.verify(mockCache).load(key)
        test.assertValue(mapper.mapToDomain(cacheList))
    }

    @Test
    fun `get users cache fail fallback remote succeeds`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.error(throwable))
        Mockito.`when`(mockApi.getUsers()).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(false).test()

        // then
        Mockito.verify(mockCache).load(key)
        Mockito.verify(mockCache).save(key, remoteList)
        Mockito.verify(mockApi).getUsers()
        test.assertValue(mapper.mapToDomain(remoteList))
    }

    @Test
    fun `get user cache success`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.just(cacheList))

        // when
        val test = repository.get(userId, false).test()

        // then
        Mockito.verify(mockCache).load(key)
        test.assertValue(mapper.mapToDomain(cacheItem))
    }

    @Test
    fun `get user cache fail fallback remote succeeds`() {
        // given
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.error(throwable), Single.just(emptyList()))
        Mockito.`when`(mockApi.getUser(userId)).thenReturn(Single.just(remoteItem))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(userId, false).test()

        // then
        Mockito.verify(mockCache, Mockito.times(2)).load(key)
        Mockito.verify(mockCache).save(key, remoteList)
        Mockito.verify(mockApi).getUser(userId)
        test.assertValue(mapper.mapToDomain(remoteItem))
    }


    @Test
    fun `get users remote success`() {
        // given
        Mockito.`when`(mockApi.getUsers()).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(true).test()

        // then
        Mockito.verify(mockApi).getUsers()
        Mockito.verify(mockCache).save(key, remoteList)
        test.assertValue(mapper.mapToDomain(remoteList))
    }

    @Test
    fun `get users remote fail`() {
        // given
        Mockito.`when`(mockApi.getUsers()).thenReturn(Single.error(throwable))

        // when
        val test = repository.get(true).test()

        // then
        Mockito.verify(mockApi).getUsers()
        test.assertError(throwable)
    }

    @Test
    fun `get user remote success`() {
        // given
        Mockito.`when`(mockApi.getUser(userId)).thenReturn(Single.just(remoteItem))
        Mockito.`when`(mockCache.load(key)).thenReturn(Single.just(remoteList))
        Mockito.`when`(mockCache.save(key, remoteList)).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get(userId, true).test()

        // then
        Mockito.verify(mockApi).getUser(userId)
        test.assertValue(mapper.mapToDomain(remoteItem))
    }

    @Test
    fun `get user remote fail`() {
        // given
        Mockito.`when`(mockApi.getUser(userId)).thenReturn(Single.error(throwable))

        // when
        val test = repository.get(userId, true).test()

        // then
        Mockito.verify(mockApi).getUser(userId)
        test.assertError(throwable)
    }
}