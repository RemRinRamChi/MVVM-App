package com.yawjenn.mvvmpractice.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yawjenn.mvvmpractice.data.Post
import androidx.recyclerview.widget.DiffUtil
import com.yawjenn.mvvmpractice.databinding.PostItemBinding
import com.yawjenn.mvvmpractice.util.BindableAdapter
import com.yawjenn.mvvmpractice.util.clearAndAddAll


class PostListAdapter(
    private val postsViewModel: PostsViewModel
) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>(), BindableAdapter<List<Post>> {

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

    override fun setData(data: List<Post>) {
        val diffResult = DiffUtil.calculateDiff(PostListDiffCallback(this.posts, data))

        this.posts.clearAndAddAll(data)

        diffResult.dispatchUpdatesTo(this)
    }

}


