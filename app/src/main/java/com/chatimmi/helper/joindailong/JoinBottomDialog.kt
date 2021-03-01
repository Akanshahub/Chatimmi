package com.chatimmi.helper.joindailong

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseBottomDialog
import com.chatimmi.databinding.ActivityJoinBottomDailongBinding
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse

class JoinBottomDialog(val group: GroupListResponse.Data.Group,val listner: onItemCkick) : BaseBottomDialog(),CommonTaskPerformer {
    private val TAG = "JoinBottomDialog"
    private var viewModel: JoinViewModel? = null
    lateinit var myValue:String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: ActivityJoinBottomDailongBinding = DataBindingUtil.inflate(inflater, R.layout.activity_join_bottom_dailong, container, false)
        val joinViewRespository = JoinRespository(baseActivity!!)
        val factory = JoinViewModelFactory(joinViewRespository)
        viewModel = ViewModelProviders.of(this,factory).get(JoinViewModel::class.java)
        binding.joinViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel?.init(this,group)

        Log.d(TAG, "onCreateView: ${group}")

        if(group.groupScope==1){
            binding.item.text = getString(R.string.join_text_public)
        }else{
            binding.item.text = getString(R.string.join_text)
        }


        
        joinViewRespository.getConnectGroupResponseData().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as JoinGroupResponse
                        listner.clicked()
                        dismiss()

                    }
                    is UIStateManager.Error -> {
                        baseActivity?. showToast(it.msg)
                    }
                    is UIStateManager.Loading -> {
                        if (it.shouldShowLoading) {
                            baseActivity?.showLoader()
                        } else {
                            baseActivity?.hideLoader()
                        }

                    }
                    is UIStateManager.ErrorCode ->{
                      baseActivity!!.callLogoutApi()
                    }
                    else -> {
                    }
                }

            }
        })

        return binding.root
    }
    interface onItemCkick{
         fun clicked()

    }
    fun show(fragmentManager: FragmentManager?) {
        if (fragmentManager != null) {
            super.show(fragmentManager, TAG)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
       arguments?.getString("groupId",myValue)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun <T> performAction(clazz: Class<T>, bundle: Bundle?, isRequried: Boolean) {

        Intent(requireContext(), clazz,).apply {
            if(isRequried){
                this.putExtras(bundle!!)
            }
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {
        requireContext().showToast(msg)
    }

    override fun dismissDialog() {
    this.dismiss()
    }

    override fun launchAction() {

    }

    override fun connectClick(userID: Int) {

    }
}

