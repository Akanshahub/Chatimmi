package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.annotation.SuppressLint
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
import com.chatimmi.databinding.FragmentMyChatGroupsBinding


private const val ARG_PARAM1 = "param1"

class MyChatGroupsFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: MyChatGroupImmigrationViewModel? = null

    lateinit var binding: FragmentMyChatGroupsBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_chat_groups, container, false)
        setupBindings(savedInstanceState)

        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[MyChatGroupImmigrationViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
        }



        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->

            if (binding.checkbox.isChecked) {

                binding.ConnectedCheckbox.setTextColor(Color.parseColor("#999999"));
                binding.checkbox.setTextColor(Color.BLACK);

            }
            if (binding.ConnectedCheckbox.isChecked) {

                binding.checkbox.setTextColor(Color.parseColor("#999999"));
                binding.ConnectedCheckbox.setTextColor(Color.BLACK);

            }
        });



        binding.model = viewModel


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                MyChatGroupsFragment().apply {
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