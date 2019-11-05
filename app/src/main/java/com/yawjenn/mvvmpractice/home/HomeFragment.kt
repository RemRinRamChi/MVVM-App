package com.yawjenn.mvvmpractice.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.tasks.TasksFragment
import com.yawjenn.mvvmpractice.util.bindEditTextData
import com.yawjenn.mvvmpractice.util.bindTextData
import com.yawjenn.mvvmpractice.util.obtainViewModel
import com.yawjenn.mvvmpractice.util.replaceFragment
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
            tvMessage.bindTextData(viewLifecycleOwner, message)

            tvUserName.bindTextData(viewLifecycleOwner, userName)
            tvEmail.bindTextData(viewLifecycleOwner, email)

            etUserId.bindEditTextData(viewLifecycleOwner, userId)

            btnLoadUser.setOnClickListener { loadUser() }
            btnEnterUser.setOnClickListener { enterUser() }

            enterUserEvent.observe(viewLifecycleOwner, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    Toast.makeText(context, "Entering with User $it", Toast.LENGTH_LONG).show()
                    enterUserScreen(it)
                }
            })
        }

        homeViewModel.start()
    }

    private fun enterUserScreen(userId: String){
        replaceFragment(TasksFragment.newInstance(userId))
    }
}
