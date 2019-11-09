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
import com.yawjenn.mvvmpractice.databinding.PostsFragmentBinding
import com.yawjenn.mvvmpractice.util.obtainFragmentViewModel
import kotlinx.android.synthetic.main.posts_fragment.*

class PostsFragment : Fragment() {

    private lateinit var binding: PostsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.posts_fragment, container, false)

        with(binding){
            lifecycleOwner = viewLifecycleOwner
            viewModel = obtainFragmentViewModel(PostsViewModel::class.java)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel?.run {
            val postListAdapter = PostListAdapter(this)
            rvPosts.adapter = postListAdapter
            rvPosts.layoutManager = LinearLayoutManager(context)

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
