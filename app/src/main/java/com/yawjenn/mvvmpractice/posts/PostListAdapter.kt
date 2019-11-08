package com.yawjenn.mvvmpractice.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yawjenn.mvvmpractice.data.Post
import com.yawjenn.mvvmpractice.databinding.ItemPostBinding
import androidx.recyclerview.widget.DiffUtil


class PostListAdapter(
    private val _postsViewModel: PostsViewModel
) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    private val posts = mutableListOf<Post>() // Cached copy of posts

    inner class PostViewHolder(private val _binding: ItemPostBinding) : RecyclerView.ViewHolder(_binding.root) {

        fun bind(post: Post, postsViewModel: PostsViewModel){
            _binding.post = post
            _binding.postsViewModel = postsViewModel
            _binding.executePendingBindings() // important to execute immediately before RecyclerView calculates layout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int)
            = holder.bind(posts[position], _postsViewModel)

    fun updatePosts(posts: List<Post>) {
        val diffResult = DiffUtil.calculateDiff(PostListDiffCallback(this.posts, posts))

        this.posts.clear()
        this.posts.addAll(posts)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = posts.size
}


