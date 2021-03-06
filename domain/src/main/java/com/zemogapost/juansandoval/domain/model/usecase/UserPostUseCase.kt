package com.zemogapost.juansandoval.domain.model.usecase

import com.zemogapost.juansandoval.domain.model.Post
import com.zemogapost.juansandoval.domain.model.User
import com.zemogapost.juansandoval.domain.model.repository.PostRepository
import com.zemogapost.juansandoval.domain.model.repository.UserRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject


/**
 * The standard library provides Pair and Triple.
 * In most cases, though, named data classes are a better design choice, because they make the code more readable by providing meaningful names for properties.
 */
data class CombinedUserPost(val user: User, val post: Post)

class UsersPostsUseCase @Inject constructor(private val userRepository: UserRepository,
                                            private val postRepository: PostRepository,
                                            private val mapper: UserPostMapper) {

    fun get(refresh: Boolean): Single<List<CombinedUserPost>> = Single.zip(userRepository.get(refresh), postRepository.get(refresh),
            BiFunction { userList, postList -> mapper.map(userList, postList) })
}

class UserPostUseCase @Inject constructor(private val userRepository: UserRepository,
                                          private val postRepository: PostRepository,
                                          private val mapper: UserPostMapper) {

    fun get(userId: String, postId: String, refresh: Boolean): Single<CombinedUserPost> = Single.zip(userRepository.get(userId, refresh), postRepository.get(postId, refresh),
            BiFunction { user, post -> mapper.map(user, post) })
}

/**
 * To obtain the user from a post we need to use the userId from the post to find it in the user list.
 * This is a limitation that comes from the network API and this specific use case requires both posts and users.
 */
class UserPostMapper @Inject constructor() {

    fun map(user: User, post: Post): CombinedUserPost = CombinedUserPost(user, post)

    fun map(userList: List<User>, postList: List<Post>): List<CombinedUserPost> = postList.map { post -> CombinedUserPost(userList.first { post.userId == it.id }, post) }
}