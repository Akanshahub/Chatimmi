package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentMyConsultantsBinding


class MyConsultantsFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: MyConsultantsImmigrationViewModel? = null

    lateinit var binding: FragmentMyConsultantsBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_consultants, container, false)
        setupBindings(savedInstanceState)

        return binding.root
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[MyConsultantsImmigrationViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
        }
        binding.model = viewModel
        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->

            if (binding.checkboxImmigration.isChecked) {

                binding.checkboxImmigration.setTextColor(Color.parseColor("#999999"));
                binding.checkboxStudy.setTextColor(Color.BLACK);
                binding.rvImmigration.visibility=View.VISIBLE
                binding.rvStudy.visibility=View.GONE


            }else{
                binding.checkboxStudy.setTextColor(Color.parseColor("#999999"));
                binding.checkboxImmigration.setTextColor(Color.BLACK);
                binding.rvImmigration.visibility=View.GONE
                binding.rvStudy.visibility=View.VISIBLE
            }

        });
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                MyConsultantsFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun <T> performAction(clazz: Class<T>) {
        Intent(requireContext(), clazz).apply {
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {
        requireContext().showToast(msg)
    }

    override fun dismissDialog() {
    }

    override fun launchAction() {

    }

    override fun connectClick(userID: Int) {

    }
}