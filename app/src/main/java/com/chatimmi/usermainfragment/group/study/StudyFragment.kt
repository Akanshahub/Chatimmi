package com.chatimmi.usermainfragment.group.study

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentStudyBinding
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupActivity
import com.chatimmi.helper.joindailong.JoinBottomDialog
import com.chatimmi.usermainfragment.group.immigration.*
import com.chatimmi.usermainfragment.group.immigration.details.ImmigrationDetailsActivity

class StudyFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: StudyViewModel? = null
    lateinit var immigrationGroupRepositary: ImmigrationGroupRepositary
    lateinit var session: Session
    var searchResult = SearchResponse()
    lateinit var binding: FragmentStudyBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study, container, false)

        immigrationGroupRepositary = ImmigrationGroupRepositary(activity)
        val factory = StudyGroupViewFactory(immigrationGroupRepositary)
        viewModel = ViewModelProviders.of(this, factory)[StudyViewModel::class.java]
        binding.studyModel = viewModel
        session = Session(activity)
        viewModel?.init(this,session)
        setupBindings()
        viewModel?.getAdapterClickObserver()?.observeForever {
            it?.let {

                viewModel?.itemValueObserver
                //  JoinBottomDialog.newInstance().show(childFragmentManager)
                val joinBottomDialog = JoinBottomDialog(it, object : JoinBottomDialog.onItemCkick {
                    override fun clicked() {
                        viewModel?.clearList()
                        viewModel?.fetchUsers()
                    }
                })
                joinBottomDialog.isCancelable = false
                joinBottomDialog.show(childFragmentManager)
                //   }

            }
        }

        viewModel?.getAdapterCardObserver()?.observeForever {
            it?.let {
                it?.let {
                    val intent = Intent(context, ImmigrationDetailsActivity::class.java)
                    intent.putExtra("groupName", it.groupName)
                    intent.putExtra("categoryName", it.categoryName)
                    intent.putExtra("subCategoryName", it.subCategoryName)
                    startActivity(intent)
                }

            }
        }
        binding.filter.setOnClickListener {
            val intent = Intent(getActivity(), FilterGroupActivity::class.java)
            //listener?.onClick(AlbumsData)
            intent.putExtra("search", searchResult)
            startActivityForResult(intent, 2)
        }
        return binding.root
    }

    private fun setupBindings() {
        immigrationGroupRepositary.getResponseData().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GroupListResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data?.groupList!!.size}")


                        if (getData.data!!.groupList.size ==0) {
                            viewModel?.clearList()
                        } else {
                            viewModel?.getAdapter()?.let {
                                binding.rvMain.adapter = viewModel?.getAdapter()
                                viewModel?.getAdapter()!!.addData(getData.data!!.groupList)
                                viewModel?.getAdapter()!!.notifyDataSetChanged()
                            }
                        }


                    }
                    is UIStateManager.Error -> {
                        showMsg(it.msg)
                    }
                    else -> {

                    }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                StudyFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
                searchResult = data?.getParcelableExtra("searchResult")!!
                var categoryId=""
                var subCategoryId=""
                var groupScope=""
                categoryId=searchResult.category!!
                subCategoryId=searchResult.subcategory!!
                groupScope=searchResult.group_scope!!
                binding.rvMain.adapter = viewModel?.getAdapter()
                viewModel!!.sendData(categoryId,subCategoryId,groupScope)
                viewModel?.getAdapter()!!.notifyDataSetChanged()
                Log.d("TAG", "searchResult: " + searchResult)


            }
        } catch (ex: Exception) {
            Toast.makeText(context, ex.toString(),
                    Toast.LENGTH_SHORT).show()
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


}

/*
class ImmigrationFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: GroupViewModel? = null
    lateinit var binding: FragmentImmigrationBinding
    lateinit var immigrationGroupRepositary: ImmigrationGroupRepositary
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_immigration, container, false)
        immigrationGroupRepositary = ImmigrationGroupRepositary(activity)
        val factory = ImmigrationGroupViewFactory(immigrationGroupRepositary)
        viewModel = ViewModelProviders.of(this, factory)[GroupViewModel::class.java]
        binding.model = viewModel
        viewModel?.init(this)
        setupBindings()
        viewModel?.getAdapterClickObserver()?.observeForever {
            it?.let {
                if (it) {
                    //  JoinBottomDialog.newInstance().show(childFragmentManager)
                    val joinBottomDialog = JoinBottomDialog()
                    joinBottomDialog.isCancelable = false
                    joinBottomDialog.show(childFragmentManager)
                }
            }
        }
        binding.filter.setOnClickListener {
            val intent = Intent(getActivity(), FilterGroupActivity::class.java)
            intent.putExtra("dd", "ff")
            getActivity()?.startActivity(intent)
        }
        return binding.root
    }

    private fun setupBindings() {
        immigrationGroupRepositary.getResponseData().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GroupListResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data.groupList.size}")


                        viewModel?.getAdapter()?.let {

                            binding.rvMain.adapter =viewModel?.getAdapter()
                            viewModel?.getAdapter()!!.addData(getData.data.groupList)
                            viewModel?.getAdapter()!!.notifyDataSetChanged()
                        }




                    }
                    is UIStateManager.Error -> {
                        showMsg(it.msg)
                    }
                    else -> {

                    }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                ImmigrationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
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
}*/
