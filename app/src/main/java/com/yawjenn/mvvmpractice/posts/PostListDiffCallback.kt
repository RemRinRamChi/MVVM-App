package com.yawjenn.mvvmpractice.posts

import androidx.recyclerview.widget.DiffUtil
import com.yawjenn.mvvmpractice.data.Post

class PostListDiffCallback(private val oldPosts: List<Post>, private val newPosts: List<Post>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldPosts.size

    override fun getNewListSize(): Int = newPosts.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldPosts[oldItemPosition].id == newPosts[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldPosts[oldItemPosition] == newPosts[newItemPosition]
}