package com.yawjenn.mvvmpractice.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.ViewModelFactory
import com.yawjenn.mvvmpractice.util.bindTextData
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(activity))[HomeViewModel::class.java]

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.run {
            tvMessage.bindTextData(this@HomeFragment, status)

            tvUserName.bindTextData(this@HomeFragment, userName)
            tvEmail.bindTextData(this@HomeFragment, email)

            etUserId.bindEditTextData(this@HomeFragment, userId)

            btnLoadUser.setOnClickListener { loadUser() }
        }

        homeViewModel.start()
    }

    companion object{
        fun newInstance() = HomeFragment()
    }
}
