package com.yawjenn.mvvmpractice.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.util.bindTextData
import com.yawjenn.mvvmpractice.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : Fragment() {

    companion object {
        const val USER_ID = "USER_ID"

        fun newInstance(userId: String) : PostsFragment{
            return PostsFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userId)
                }
            }
        }
    }

    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = obtainViewModel(PostsViewModel::class.java)

        val postListAdapter = PostListAdapter(viewModel)
        rvPosts.adapter = postListAdapter
        rvPosts.layoutManager = LinearLayoutManager(context)


        viewModel.run {
            tvUserLabel.bindTextData(viewLifecycleOwner, userIdLabel)
            tvPostUnreadCountLabel.bindTextData(viewLifecycleOwner, unreadPostCountLabel)

            userPosts.observe(viewLifecycleOwner, Observer {
                postListAdapter.setPosts(it)
            })
        }

        viewModel.loadUser(arguments?.getString(USER_ID))

    }

}
