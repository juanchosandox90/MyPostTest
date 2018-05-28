package com.zemogaposttest.juansandoval.zemogaposttest.postlist

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.zemogaposttest.juansandoval.zemogaposttest.R
import com.zemogaposttest.juansandoval.zemogaposttest.inflate
import com.zemogaposttest.juansandoval.zemogaposttest.loadAvatar
import com.zemogaposttest.juansandoval.zemogaposttest.model.PostItem
import kotlinx.android.synthetic.main.include_user_info.view.*
import kotlinx.android.synthetic.main.item_list_post.view.*
import java.util.ArrayList


class PostListAdapter constructor(private val avatarClick: (String) -> Unit, private val itemClick: (PostItem) -> Unit)
    : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    private val items = ArrayList<PostItem>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_post)) {

        fun bind(item: PostItem) {
            itemView.userAvatar.loadAvatar(item.email)
            itemView.userUsername.text = "@${item.username}"
            itemView.userName.text = item.name
            itemView.postTitle.text = item.title.capitalize()
            itemView.postBody.text = item.body.capitalize()
            itemView.userAvatar.setOnClickListener { avatarClick.invoke(item.userId) }
            itemView.setOnClickListener { itemClick.invoke(item) }
        }
    }

    fun addItems(list: List<PostItem>) {
        this.items.clear()
        this.items.addAll(list)
        notifyDataSetChanged()
    }
}