package com.chatimmi.usermainfragment.otherfragment.myProfile

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
import com.chatimmi.databinding.FragmentMyConsultantsBinding


class MyConsultantsFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: MyConsultantsImmigrationViewModel? = null
    lateinit var myConsultantsRepository: MyConsultantsRepository
    lateinit var binding: FragmentMyConsultantsBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_consultants, container, false)

        myConsultantsRepository = MyConsultantsRepository(activity)
        val factory = MyConsultantsViewModelFactory(myConsultantsRepository)
        viewModel = ViewModelProviders.of(this, factory)[MyConsultantsImmigrationViewModel::class.java]
        binding.model = viewModel
        viewModel?.init(this)
        setupBindings()
        return binding.root
    }

    private fun setupBindings() {
        myConsultantsRepository.getResponseData().observe(activity, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GetProfileMyConsultantsResonse
                        Log.d("bnjnknk", "setupBindings: ${getData.data!!.list.size}")


                        viewModel?.getAdapter()?.let {
                            binding.rvImmigration.visibility = View.VISIBLE
                            binding.rvStudy.visibility = View.GONE
                            binding.rvImmigration.adapter = viewModel?.getAdapter()
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

            if (binding.checkboxImmigration.isChecked) {

                binding.checkboxStudy.setTextColor(Color.parseColor("#999999"));
                binding.checkboxImmigration.setTextColor(Color.BLACK)
                binding.rvImmigration.visibility = View.VISIBLE
                binding.rvStudy.visibility = View.GONE
                myConsultantsRepository.getResponseData().observe(activity, Observer {
                    it?.let {
                        when (it) {
                            is UIStateManager.Success<*> -> {
                                val getData = it.data as GetProfileMyConsultantsResonse
                                Log.d("bnjnknk", "setupBindings: ${getData.data!!.list.size}")


                                viewModel?.getAdapter()?.let {
                                    binding.rvImmigration.visibility = View.VISIBLE
                                    binding.rvStudy.visibility = View.GONE
                                    binding.rvImmigration.adapter = viewModel?.getAdapter()
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


            }
            if (binding.checkboxStudy.isChecked) {

                binding.checkboxImmigration.setTextColor(Color.parseColor("#999999"));
                binding.checkboxStudy.setTextColor(Color.BLACK);
                binding.rvImmigration.visibility = View.GONE
                binding.rvStudy.visibility = View.VISIBLE
                myConsultantsRepository.getResponseData().observe(activity, Observer {
                    it?.let {
                        when (it) {
                            is UIStateManager.Success<*> -> {
                                val getData = it.data as GetProfileMyConsultantsResonse
                                Log.d("bnjnknk", "setupBindings: ${getData.data!!.list.size}")


                                viewModel?.getAdapter1()?.let {
                                    binding.rvImmigration.visibility = View.GONE
                                    binding.rvStudy.visibility = View.VISIBLE
                                    binding.rvStudy.adapter = viewModel?.getAdapter1()
                                    viewModel?.getAdapter1()!!.addData(getData.data!!.list)
                                    viewModel?.getAdapter1()!!.notifyDataSetChanged()
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