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
import com.yawjenn.mvvmpractice.guess.GuessFragment
import com.yawjenn.mvvmpractice.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModel by lazy {
        obtainViewModel(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = homeViewModel
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.run {
            enterUserEvent.observe(viewLifecycleOwner, Observer { event ->
                event.handleIfNotHandled {
                    enterUserScreen(it)
                }
            })
            guessWordEvent.observe(viewLifecycleOwner, Observer { event ->
                event.handleIfNotHandled {
                    enterGuessWordScreen()
                }
            })
        }
    }

    private fun enterUserScreen(userId: String) {
        replaceFragment(PostsFragment.newInstance(userId))
    }

    private fun enterGuessWordScreen() {
        replaceFragment(GuessFragment.newInstance())
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
