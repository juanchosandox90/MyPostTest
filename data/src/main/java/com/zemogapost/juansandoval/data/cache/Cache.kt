package com.zemogapost.juansandoval.data.cache

import com.pacoworks.rxpaper2.RxPaperBook
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.io
import javax.inject.Inject


class Cache<T> @Inject constructor() {

    fun load(key: String): Single<T> = RxPaperBook.with(io()).read(key)
    fun save(key: String, anyObject: T): Single<T> = RxPaperBook.with(io()).write(key, anyObject).toSingleDefault(anyObject)
}