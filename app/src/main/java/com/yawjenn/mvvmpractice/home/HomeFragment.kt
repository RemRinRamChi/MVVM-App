package com.yawjenn.mvvmpractice.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.databinding.FragmentHomeBinding
import com.yawjenn.mvvmpractice.posts.PostsFragment
import com.yawjenn.mvvmpractice.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel : HomeViewModel by lazy {
        obtainViewModel(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        with(binding){
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.enterUserEvent.observe(viewLifecycleOwner, Observer { event ->
            event.handleIfNotHandled {
                enterUserScreen(it)
            }
        })
    }

    private fun enterUserScreen(userId: String){
        replaceFragment(PostsFragment.newInstance(userId))
    }

    companion object{
        fun newInstance() = HomeFragment()
    }
}
