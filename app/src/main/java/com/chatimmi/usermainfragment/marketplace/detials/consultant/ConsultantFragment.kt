package com.chatimmi.usermainfragment.marketplace.detials.consultant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentConsultantBinding
import com.chatimmi.databinding.FragmentSchoolBinding
import com.chatimmi.usermainfragment.group.immigration.ImmigrationFragment
import com.chatimmi.usermainfragment.marketplace.detials.school.SchoolViewModel

private const val ARG_PARAM1 = "param1"

class ConsultantFragment: BaseFragment()  {
    private var viewModel: ConsultantViewModel? = null

    lateinit var binding: FragmentConsultantBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_consultant, container, false)
        viewModel = ViewModelProviders.of(this).get(ConsultantViewModel::class.java)
        binding.model = viewModel
        setupBindings(savedInstanceState)
        return binding.root
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[ConsultantViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }
        binding.model = viewModel
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                ConsultantFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }
}