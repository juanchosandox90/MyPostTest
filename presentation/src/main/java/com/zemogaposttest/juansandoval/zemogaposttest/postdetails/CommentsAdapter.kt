package com.zemogaposttest.juansandoval.zemogaposttest.postdetails

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.zemogaposttest.juansandoval.zemogaposttest.R
import com.zemogaposttest.juansandoval.zemogaposttest.inflate
import com.zemogaposttest.juansandoval.zemogaposttest.loadAvatar
import com.zemogaposttest.juansandoval.zemogaposttest.model.CommentItem
import kotlinx.android.synthetic.main.include_user_info_small.view.*
import kotlinx.android.synthetic.main.item_list_comment.view.*
import java.util.ArrayList
import javax.inject.Inject


class CommentsAdapter @Inject constructor() : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private val items = ArrayList<CommentItem>()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_list_comment)) {

        fun bind(item: CommentItem) {
            itemView.userAvatar.loadAvatar(item.email)
            itemView.userName.text = item.name.capitalize()
            itemView.commentBody.text = item.body.capitalize()
        }
    }

    fun addItems(list: List<CommentItem>) {
        this.items.clear()
        this.items.addAll(list)
        notifyDataSetChanged()
    }
}