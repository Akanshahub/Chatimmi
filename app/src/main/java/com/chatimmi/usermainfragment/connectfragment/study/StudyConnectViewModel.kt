package com.chatimmi.usermainfragment.connectfragment.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.connectfragment.details.ConnectDetailsActivity

class StudyConnectViewModel  : ViewModel(){
    lateinit var commonTaskPerformer: CommonTaskPerformer
  private lateinit var adapter: StudyConnectAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
        fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
        fun init(commonTaskPerformer: CommonTaskPerformer) {
            this.commonTaskPerformer=commonTaskPerformer
            adapter = object : StudyConnectAdapter(R.layout.single_item_study_connect, this){
                override fun onConnectCall() {
                   // commonTaskPerformer.showMsg("Under Development")
                    commonTaskPerformer.performAction(ConnectDetailsActivity::class.java)
                }
            }
        }
        fun getAdapter(): StudyConnectAdapter? {
            return adapter
        }
    }
