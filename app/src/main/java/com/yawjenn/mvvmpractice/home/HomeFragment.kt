package com.yawjenn.mvvmpractice.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.databinding.HomeFragmentBinding
import com.yawjenn.mvvmpractice.posts.PostsFragment
import com.yawjenn.mvvmpractice.guess.GuessFragment
import com.yawjenn.mvvmpractice.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = obtainFragmentViewModel(HomeViewModel::class.java)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel?.run {
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
