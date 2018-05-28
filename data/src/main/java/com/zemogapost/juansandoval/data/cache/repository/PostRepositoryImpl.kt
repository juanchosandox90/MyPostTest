package com.zemogapost.juansandoval.data.cache.repository

import com.zemogapost.juansandoval.data.cache.Cache
import com.zemogapost.juansandoval.data.cache.model.PostEntity
import com.zemogapost.juansandoval.data.cache.model.PostMapper
import com.zemogapost.juansandoval.data.cache.remote.PostsApi
import com.zemogapost.juansandoval.domain.model.Post
import com.zemogapost.juansandoval.domain.model.repository.PostRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PostRepositoryImpl @Inject constructor(private val api: PostsApi,
                                             private val cache: Cache<List<PostEntity>>,
                                             private val mapper: PostMapper) : PostRepository {
    override val key = "Post List"

    override fun get(refresh: Boolean): Single<List<Post>> = when (refresh) {
        true -> api.getPosts().flatMap { set(it) }.map { mapper.mapToDomain(it) }
        false -> cache.load(key).map { mapper.mapToDomain(it) }.onErrorResumeNext { get(true) }
    }

    override fun get(postId: String, refresh: Boolean): Single<Post> = when (refresh) {
        true -> api.getPost(postId).flatMap { set(it) }.map { mapper.mapToDomain(it) }
        false -> cache.load(key).map { it.first { it.id == postId } }.map { mapper.mapToDomain(it) }.onErrorResumeNext { get(postId, true) }
    }

    private fun set(list: List<PostEntity>) = cache.save(key, list)

    private fun set(entity: PostEntity) = cache.load(key).map { it.filter { it.id != entity.id }.plus(entity) }.flatMap { set(it) }.map { entity }
}