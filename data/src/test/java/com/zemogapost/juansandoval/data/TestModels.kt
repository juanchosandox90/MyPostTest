package com.zemogapost.juansandoval.data

import com.zemogapost.juansandoval.data.cache.model.*
import com.zemogapost.juansandoval.domain.model.*


fun createUserEntity(): UserEntity = UserEntity("1", "name", "username", "email", createAddressEntity(), "phone", "website", createCompanyEntity())
fun createGeoEntity(): GeoEntity = GeoEntity("0.0", "0.0")
fun createAddressEntity(): AddressEntity = AddressEntity("street", "suite", "city", "zipcode", createGeoEntity())
fun createCompanyEntity(): CompanyEntity = CompanyEntity("name", "catchPhrase", "bs")

fun createPostEntity(): PostEntity = PostEntity("1", "1", "title", "body")

fun createCommentEntity(): CommentEntity = CommentEntity("1", "1", "name", "email", "body")

fun createUser(): User = User("1", "name", "username", "email", createAddress(), "phone", "website", createCompany())
fun createGeo(): Geo = Geo("0.0", "0.0")
fun createAddress(): Address = Address("street", "suite", "city", "zipcode", createGeo())
fun createCompany(): Company = Company("name", "catchPhrase", "bs")

fun createPost(): Post = Post("1", "1", "title", "body")

fun createComment(): Comment = Comment("1", "1", "name", "email", "body")