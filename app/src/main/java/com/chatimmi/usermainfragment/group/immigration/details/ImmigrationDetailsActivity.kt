package com.chatimmi.usermainfragment.group.immigration.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityImmigrationDetailsBinding
import com.chatimmi.retrofitnetwork.ApiCallback

import com.chatimmi.usermainfragment.group.immigration.GroupListResponse


class ImmigrationDetailsActivity : BaseActivitykt() , CommonTaskPerformer,ApiCallback.ImmigrationDetailsCallBack{

    private var binding: ActivityImmigrationDetailsBinding? = null
    private var viewModel: ImmigrationDetailsViewModel? = null
    var groupName= ""
    private var userId = 0
    var categoryName =""
    var subCategoryName= ""
    var groupId: String = ""
    var isgroupconnect=0
    var isConnected=0
    var position=0
    lateinit var groupMember: ArrayList<ImmigrationDetailsResponse.Data.GroupMember>

    var group = GroupListResponse.Data.Group()

    var isJoinClicked=false
    lateinit var immigrationDetailsRepositary: ImmigrationDetailsRepositary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_immigration_details)
        immigrationDetailsRepositary = ImmigrationDetailsRepositary(this,this)
        val factory = ImmigrationDetailsViewFactoryModel(immigrationDetailsRepositary)
        viewModel = ViewModelProviders.of(this, factory)[ImmigrationDetailsViewModel::class.java]

        groupId = intent.getStringExtra("groupId").toString()
        groupName = intent.getStringExtra("groupName").toString()
        categoryName = intent.getStringExtra("categoryName").toString()
        isgroupconnect = intent.getIntExtra("is_group_connect",0)
        subCategoryName = intent.getStringExtra("subCategoryName").toString()
        position = intent.getIntExtra("position",0)
        setupBindings()
        viewModel?.init(groupId,this)
        binding!!.studyModel = viewModel
        groupMember=ArrayList()

        binding?.uKImmigration?.text = subCategoryName
        if (isgroupconnect ==1) {
            binding!!.tvRequest.visibility = View.GONE
            binding!!.joinChat.visibility = View.VISIBLE
            binding!!.joinButton.visibility = View.GONE
        } else if (isgroupconnect==2) {
            binding!!.tvRequest.visibility = View.VISIBLE
            binding!!.joinChat.visibility = View.GONE
            binding!!.joinButton.visibility = View.GONE
        } else {
            binding!!.tvRequest.visibility = View.GONE
            binding!!.joinChat.visibility = View.GONE
            binding!!.joinButton.visibility = View.VISIBLE
        }

    }

    private fun setupBindings() {

        immigrationDetailsRepositary.getResponseData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ImmigrationDetailsResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data?.groupMember?.size}")
                        groupMember.addAll(getData.data!!.groupMember)
                        binding?.name?.text = groupName
                        binding?.conoda?.text = categoryName
                        isConnected= getData.data!!.isGroupConnect!!
                        if (getData.data!!.groupData!!.groupScope == 1) {
                            binding!!.lock.visibility = View.GONE
                        } else {
                            binding!!.lock.visibility = View.VISIBLE
                        }

                        viewModel?.getAdapter()?.let {
                            binding?.rvMain?.visibility = View.VISIBLE
                            binding?.rvMain?.adapter = viewModel?.getAdapter()
                            viewModel?.getAdapter()!!.addData(groupMember)
                            viewModel?.getAdapter()!!.notifyDataSetChanged()

                        }
                    }
                    is UIStateManager.Error -> {
                        toastMessage(it.msg, this)
                        //showMsg(it.msg)
                    }
                    is UIStateManager.Loading -> {
                        if (it.shouldShowLoading) {
                            showLoader()
                        } else {
                            hideLoader()
                        }

                    }
                    else -> {

                    }
                }
            }
        })
        immigrationDetailsRepositary.getResponseDataConnectClick().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val position = groupMember.indexOf(groupMember.filter { it.userID!! == this.userId }[0])
                        groupMember[position].isConnect = 1
                        viewModel!!.getAdapter()!!.addData(groupMember)
                    }
                    is UIStateManager.Error -> {
                        showMsg(it.msg)
                    }
                    is UIStateManager.Loading -> {
                        if (it.shouldShowLoading) {
                            showLoader()
                        } else {
                            hideLoader()
                        }

                    }
                    else -> {

                    }
                }
            }
        })
        binding!!.joinButton.setOnClickListener {
            immigrationDetailsRepositary.getConnectGroupResponseData().observe(this, Observer {
                it?.let {
                    when (it) {
                        is UIStateManager.Success<*> -> {
                            if (isConnected == 1) {
                                binding!!.tvRequest.visibility = View.GONE
                                binding!!.joinChat.visibility = View.VISIBLE
                                binding!!.joinButton.visibility = View.GONE
                            } else if (isConnected == 2) {
                                binding!!.tvRequest.visibility = View.VISIBLE
                                binding!!.joinChat.visibility = View.GONE
                                binding!!.joinButton.visibility = View.GONE
                            } else {
                                binding!!.tvRequest.visibility = View.GONE
                                binding!!.joinChat.visibility = View.GONE
                                binding!!.joinButton.visibility = View.VISIBLE
                            }
                            isJoinClicked=true
                        }
                        is UIStateManager.Error -> {
                            showToast(it.msg)
                        }
                        is UIStateManager.Loading -> {
                            if (it.shouldShowLoading) {
                                showLoader()
                            } else {
                                hideLoader()
                            }

                        }
                        else -> {
                        }
                    }

                }
            })

        }
        binding!!.appBar.ivBtnBack.setOnClickListener() {
       if(isJoinClicked){
           val intent = Intent()
           intent.putExtra("position",position)
           setResult(Activity.RESULT_OK,intent)
           finish()
       }else{
           onBackPressed()
       }

        }

    }

    override fun <T> performAction(clazz: Class<T>, bundle: Bundle?, isRequried: Boolean) {

        Intent(this, clazz,).apply {
            if(isRequried){
                this.putExtras(bundle!!)
            }
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {

    }

    override fun dismissDialog() {

    }

    override fun launchAction() {

    }

    override fun connectClick(userID: Int) {
        this.userId = userID
        viewModel?.setConnectConsultantAPI(userID.toString())
    }

    override fun onSuccessLogin(immigrationDetailsResponse: ImmigrationDetailsResponse) {

    }

    override fun onShowBaseLoader() {

    }

    override fun onHideBaseLoader() {
    }

    override fun onError(errorMessage: String) {
       toastMessage(errorMessage,this)
    }
}