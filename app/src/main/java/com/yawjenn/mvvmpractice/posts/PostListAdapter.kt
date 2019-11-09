package com.yawjenn.mvvmpractice.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yawjenn.mvvmpractice.data.Post
import androidx.recyclerview.widget.DiffUtil
import com.yawjenn.mvvmpractice.databinding.PostItemBinding


class PostListAdapter(
    private val postsViewModel: PostsViewModel
) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    private val posts = mutableListOf<Post>() // Cached copy of posts

    inner class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post, postsViewModel: PostsViewModel){
            binding.post = post
            binding.postsViewModel = postsViewModel

            binding.executePendingBindings() // important to execute immediately before RecyclerView calculates layout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int)
            = holder.bind(posts[position], postsViewModel)

    override fun getItemCount() = posts.size

    fun updatePosts(posts: List<Post>) {
        val diffResult = DiffUtil.calculateDiff(PostListDiffCallback(this.posts, posts))

        this.posts.clear()
        this.posts.addAll(posts)

        diffResult.dispatchUpdatesTo(this)
    }

}


