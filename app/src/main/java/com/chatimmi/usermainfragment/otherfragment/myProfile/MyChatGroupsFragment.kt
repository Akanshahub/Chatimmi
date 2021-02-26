package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentMyChatGroupsBinding


private const val ARG_PARAM1 = "param1"

class MyChatGroupsFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: MyChatGroupImmigrationViewModel? = null
    lateinit var myChatGroupRepository: MyChatGroupRepository
    lateinit var binding: FragmentMyChatGroupsBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_chat_groups, container, false)

        myChatGroupRepository = MyChatGroupRepository(activity)
        val factory = MyChatGroupViewModalFactory(myChatGroupRepository)
        viewModel = ViewModelProviders.of(this, factory)[MyChatGroupImmigrationViewModel::class.java]
        binding.model = viewModel
        viewModel?.init(this)
        setupBindings()
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    private fun setupBindings() {
        myChatGroupRepository.getResponseData().observe(activity, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GetProfileChatGroupResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data!!.list!!.size}")


                        viewModel?.getAdapter()?.let {
                            binding.rvMain.visibility = View.VISIBLE
                            binding.rvMain.adapter = viewModel?.getAdapter()
                            viewModel?.getAdapter()!!.addData(getData.data!!.list)
                            viewModel?.getAdapter()!!.notifyDataSetChanged()
                        }


                    }
                    is UIStateManager.Error -> {
                        showMsg(it.msg)
                    }
                    is UIStateManager.Loading -> {
                        if (it.shouldShowLoading) {
                            activity.showLoader()
                        } else {
                            activity.hideLoader()
                        }

                    }
                    else -> {

                    }
                }
            }
        })


        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            if (binding.checkbox.isChecked) {
                binding.ConnectedCheckbox.setTextColor(Color.parseColor("#999999"));
                binding.checkbox.setTextColor(Color.BLACK);
                viewModel!!.fetchStudyDetials()
                viewModel?.getAdapter()!!.notifyDataSetChanged()

            }
            if (binding.ConnectedCheckbox.isChecked) {
                binding.checkbox.setTextColor(Color.parseColor("#999999"));
                binding.ConnectedCheckbox.setTextColor(Color.BLACK);
                viewModel!!.fetchImmiDetials()
                viewModel?.getAdapter()!!.notifyDataSetChanged()
            }
        });
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                MyChatGroupsFragment().apply {
                    arguments = Bundle().apply {
                    }

                }
    }

    override fun <T> performAction(clazz: Class<T>, bundle: Bundle?, isRequried: Boolean) {

        Intent(requireContext(), clazz).apply {
            if (isRequried) {
                this.putExtras(bundle!!)
            }
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