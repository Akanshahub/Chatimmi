package com.chatimmi.usermainfragment.connectfragment.immigrationconnect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.connectfragment.details.ConnectDetailsActivity

class ImmigrationConnectViewModel : ViewModel(){
    private lateinit var commonTaskPerformer: CommonTaskPerformer
    private lateinit var adapter: ImmigrationConnectAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getConnectClickObserver() = itemClickObserver as LiveData<Boolean>
    fun init(commonTaskPerformer: CommonTaskPerformer){
        this.commonTaskPerformer=commonTaskPerformer
        adapter = object : ImmigrationConnectAdapter(R.layout.single_item_connect_immigration, this){
            override fun onConnectCallBack() {
               // commonTaskPerformer.showMsg("Under Development")
                commonTaskPerformer.performAction(ConnectDetailsActivity::class.java)
            }
        }
    }
    fun getAdapter(): ImmigrationConnectAdapter? {
        return adapter
    }
}
