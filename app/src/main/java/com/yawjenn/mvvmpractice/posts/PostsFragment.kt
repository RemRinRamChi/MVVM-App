package com.yawjenn.mvvmpractice.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.databinding.FragmentPostsBinding
import com.yawjenn.mvvmpractice.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_posts.*

class PostsFragment : Fragment() {

    private lateinit var binding: FragmentPostsBinding

    private val postsViewModel : PostsViewModel by lazy {
        obtainViewModel(PostsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts, container, false)

        with(binding){
            lifecycleOwner = viewLifecycleOwner
            viewModel = postsViewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val postListAdapter = PostListAdapter(postsViewModel)
        rvPosts.adapter = postListAdapter
        rvPosts.layoutManager = LinearLayoutManager(context)


        postsViewModel.run {
            userPosts.observe(viewLifecycleOwner, Observer {
                postListAdapter.updatePosts(it)
            })

            loadUser(arguments?.getString(USER_ID))
        }

    }

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
}
