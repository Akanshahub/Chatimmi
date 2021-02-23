package com.chatimmi.usermainfragment.connectfragment.immigrationconnect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.connectfragment.chat.ChatActivity
import com.chatimmi.usermainfragment.connectfragment.details.ConnectDetailsActivity
import java.util.*

class ImmigrationConnectViewModel(val repo: ImmigrationConnectRepositary) : ViewModel() {
    private lateinit var commonTaskPerformer: CommonTaskPerformer
    private lateinit var adapter: ImmigrationConnectAdapter
    private val list: ArrayList<ConsultantListResponce.Data.Consultant> = ArrayList()
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getConnectClickObserver() = itemClickObserver as LiveData<Boolean>
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer
        adapter = object : ImmigrationConnectAdapter(R.layout.single_item_connect_immigration, this, list,) {
            override fun onConnectCallBack(userID: Int) {
                commonTaskPerformer.connectClick(userID)
            }

            override fun onChatCallBack() {
                commonTaskPerformer.performAction(ChatActivity::class.java)
            }

            override fun onCardCallBack(item: ConsultantListResponce.Data.Consultant) {
                commonTaskPerformer.performAction(ConnectDetailsActivity::class.java)
            }
        }
    }

    fun getAdapter(): ImmigrationConnectAdapter? {
        return adapter
    }

    fun callConsultantConnectListApi() {
        repo.callConsultantListApi("2")
    }

    fun setConnectConsultantAPI(userId:String) {
        repo.callConnectApi(userId)
    }
}
