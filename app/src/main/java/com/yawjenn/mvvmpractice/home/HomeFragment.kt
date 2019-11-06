package com.yawjenn.mvvmpractice.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.tasks.PostsFragment
import com.yawjenn.mvvmpractice.util.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object{
        fun newInstance() = HomeFragment()
    }

    private lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel = obtainViewModel(HomeViewModel::class.java)

        homeViewModel.run {
            tvMessage.bindTextRes(viewLifecycleOwner, message)

            tvUserName.bindTextData(viewLifecycleOwner, userName)
            tvEmail.bindTextData(viewLifecycleOwner, email)

            etUserId.bindEditTextData(viewLifecycleOwner, userId)

            btnLoadUser.setOnClickListener { loadUser() }
            btnEnterUser.setOnClickListener { enterUser() }
            btnRefresh.setOnClickListener { refresh() }

            enterUserEvent.observe(viewLifecycleOwner, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    enterUserScreen(it)
                }
            })
        }
    }

    private fun enterUserScreen(userId: String){
        replaceFragment(PostsFragment.newInstance(userId))
    }
}
