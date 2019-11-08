package com.yawjenn.mvvmpractice.guess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.databinding.FragmentGuessBinding
import com.yawjenn.mvvmpractice.util.obtainViewModel

class GuessFragment : Fragment() {

    private lateinit var binding: FragmentGuessBinding

    private val guessViewModel: GuessViewModel by lazy {
        obtainViewModel(GuessViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_guess, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = guessViewModel
        }

        return binding.root
    }

    companion object {
        fun newInstance() = GuessFragment()
    }
}
