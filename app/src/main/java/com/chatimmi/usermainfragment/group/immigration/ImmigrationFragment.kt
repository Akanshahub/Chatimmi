package com.chatimmi.usermainfragment.group.immigration

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.chatimmi.databinding.FragmentImmigrationBinding
import com.chatimmi.helper.joindailong.JoinBottomDialog
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupActivity
import com.chatimmi.usermainfragment.group.immigration.details.ImmigrationDetailsActivity


private const val ARG_PARAM1 = "param1"


class ImmigrationFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: GroupViewModel? = null
    lateinit var binding: FragmentImmigrationBinding

    lateinit var group: List<GroupListResponse.Data.Group>
    lateinit var session: Session
    var searchResult = SearchResponse()

    lateinit var immigrationGroupRepositary: ImmigrationGroupRepositary
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_immigration, container, false)
        immigrationGroupRepositary = ImmigrationGroupRepositary(activity)
        group = ArrayList()

        val factory = ImmigrationGroupViewFactory(immigrationGroupRepositary)
        viewModel = ViewModelProviders.of(this, factory)[GroupViewModel::class.java]
        binding.model = viewModel
        searchFunctionality()
        session = Session(activity)
        viewModel?.init(this, session)
        setupBindings()
        viewModel?.getAdapterClickObserver()?.observeForever {
            it?.let {
                // if (it) {
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
                it.let {
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
            intent.putExtra("groupName", searchResult)
            startActivityForResult(intent, 2)
        }
        return binding.root
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 2 && resultCode ==RESULT_OK) {
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
    private fun searchFunctionality() {
        binding.etFilterField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (viewModel!!.getAdapter() != null) {
                    // searchString = s.toString()
                    //   viewModel!!.getAdapter()!!.filter(searchString)
                    /*  if (s.toString().length > 0) {
                        //  CallPagination = false
                      } else {
                        //  CallPagination = true

                      }*/
                }

            }

            override fun afterTextChanged(editable: Editable) {
            }
        })
    }


    private fun setupBindings() {
        immigrationGroupRepositary.getResponseData().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GroupListResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data!!.groupList!!.size}")


                        if (getData.data!!.groupList.size ==0) {
                            binding.rvMain.visibility=View.GONE
                            binding.noDataAvailable.visibility=View.VISIBLE
                            viewModel?.clearList()
                        } else {
                            viewModel?.getAdapter()?.let {
                                binding.rvMain.visibility=View.VISIBLE
                                binding.noDataAvailable.visibility=View.GONE
                                binding.rvMain.adapter = viewModel?.getAdapter()
                                viewModel?.getAdapter()!!.addData(getData.data!!.groupList)
                                viewModel?.getAdapter()!!.notifyDataSetChanged()
                            }
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                ImmigrationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString("groupId", viewModel?.itemValueObserver.toString())
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
}



