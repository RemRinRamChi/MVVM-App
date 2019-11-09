package com.yawjenn.mvvmpractice.guess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.databinding.GuessFragmentBinding
import com.yawjenn.mvvmpractice.util.obtainFragmentViewModel

class GuessFragment : Fragment() {

    private lateinit var binding: GuessFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.guess_fragment, container, false)

        with(binding) {
            lifecycleOwner = viewLifecycleOwner
            viewModel = obtainFragmentViewModel(GuessViewModel::class.java)
        }

        return binding.root
    }

    companion object {
        fun newInstance() = GuessFragment()
    }
}
