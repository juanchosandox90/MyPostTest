package com.zemogaposttest.juansandoval.zemogaposttest.injection.module

import com.zemogapost.juansandoval.data.cache.repository.CommentRepositoryImpl
import com.zemogapost.juansandoval.data.cache.repository.PostRepositoryImpl
import com.zemogapost.juansandoval.data.cache.repository.UserRepositoryImpl
import com.zemogapost.juansandoval.domain.model.repository.CommentRepository
import com.zemogapost.juansandoval.domain.model.repository.PostRepository
import com.zemogapost.juansandoval.domain.model.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostRepository(repository: PostRepositoryImpl): PostRepository

    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindCommentRepository(repository: CommentRepositoryImpl): CommentRepository
}