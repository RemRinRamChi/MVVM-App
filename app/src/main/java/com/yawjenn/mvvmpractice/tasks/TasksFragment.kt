package com.yawjenn.mvvmpractice.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yawjenn.mvvmpractice.R
import com.yawjenn.mvvmpractice.util.bindTextData
import com.yawjenn.mvvmpractice.util.obtainViewModel
import kotlinx.android.synthetic.main.tasks_fragment.*

const val USER_ID = "USER_ID"

class TasksFragment : Fragment() {

    companion object {
        fun newInstance(userId: String) : TasksFragment{
            return TasksFragment().apply {
                arguments = Bundle().apply {
                    putString(USER_ID, userId)
                }
            }
        }
    }

    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = obtainViewModel(TasksViewModel::class.java)

        viewModel.run {
            tvUserId.bindTextData(viewLifecycleOwner, userId)
        }

        viewModel.loadUser(arguments?.getString(USER_ID))

    }

}
