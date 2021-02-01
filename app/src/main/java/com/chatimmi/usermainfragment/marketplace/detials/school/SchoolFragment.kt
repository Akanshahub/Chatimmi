package com.chatimmi.usermainfragment.marketplace.detials.school


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentSchoolBinding
import com.chatimmi.usermainfragment.group.immigration.ImmigrationFragment

const val ARG_PARAM1 = "param1"

class SchoolFragment : BaseFragment() {
    private var viewModel: SchoolViewModel? = null

    lateinit var binding: FragmentSchoolBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_school, container, false)
        viewModel = ViewModelProviders.of(this).get(SchoolViewModel::class.java)
        binding.model = viewModel
        setupBindings(savedInstanceState)


        return binding.root
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[SchoolViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }
        binding.model = viewModel
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                SchoolFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}