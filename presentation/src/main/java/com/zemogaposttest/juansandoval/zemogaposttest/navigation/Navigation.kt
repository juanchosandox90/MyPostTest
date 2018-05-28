package com.zemogaposttest.juansandoval.zemogaposttest.navigation

import android.app.Activity
import android.content.Intent
import com.zemogaposttest.juansandoval.zemogaposttest.model.POST_ID_KEY
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItem
import com.zemogaposttest.juansandoval.zemogaposttest.model.USER_ID_KEY
import com.zemogaposttest.juansandoval.zemogaposttest.postdetails.PostDetailsActivity
import com.zemogaposttest.juansandoval.zemogaposttest.userdetails.UserDetailsActivity
import javax.inject.Inject


class PostListNavigator @Inject constructor() {

    fun navigate(activity: Activity, postItem: PostItem) {
        val intent = Intent(activity, PostDetailsActivity::class.java)
        intent.putExtra(USER_ID_KEY, postItem.userId)
        intent.putExtra(POST_ID_KEY, postItem.postId)
        activity.startActivity(intent)
    }

}

class UserDetailsNavigator @Inject constructor() {
    fun navigate(activity: Activity, userId: String) {
        val intent = Intent(activity, UserDetailsActivity::class.java)
        intent.putExtra(USER_ID_KEY, userId)
        activity.startActivity(intent)
    }
}