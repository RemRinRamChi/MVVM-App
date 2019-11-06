package com.yawjenn.mvvmpractice.tasks

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.data.Post
import com.yawjenn.mvvmpractice.util.log


class PostListAdapter(
    private val _postsViewModel: PostsViewModel
) : RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    private val posts = mutableListOf<Post>() // Cached copy of posts

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llPost: LinearLayout = itemView.findViewById(R.id.llPost)
        val tvPostTitle: TextView = itemView.findViewById(R.id.tvPostTitle)
        val tvPostBody: TextView = itemView.findViewById(R.id.tvPostBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val current = posts[position]
        with(holder){
            tvPostTitle.text = current.title
            tvPostBody.text = current.body
            llPost.setBackgroundColor(if(current.read) Color.DKGRAY else Color.WHITE)

            llPost.setOnClickListener {
                _postsViewModel.onPostToggled(current)
            }
        }
    }

    fun setPosts(posts: List<Post>) {
        this.posts.removeAll(this.posts)
        this.posts.addAll(posts)

        "set ${posts.size} Posts to ${this.posts.size}".log()

        notifyDataSetChanged()
    }

    override fun getItemCount() = posts.size
}


