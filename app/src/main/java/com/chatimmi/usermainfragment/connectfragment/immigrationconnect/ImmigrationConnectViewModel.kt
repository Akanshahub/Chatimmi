package com.chatimmi.usermainfragment.connectfragment.immigrationconnect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.connectfragment.details.ConnectDetailsActivity
import java.util.*

class ImmigrationConnectViewModel(val repo: ImmigrationConnectRepositary) : ViewModel() {
    private lateinit var commonTaskPerformer: CommonTaskPerformer
    private lateinit var adapter: ImmigrationConnectAdapter
     val list: ArrayList<ConsultantListResponce.Data.Consultant> = ArrayList()
    var itemObserver = MutableLiveData<ConsultantListResponce.Data.Consultant>()
    fun getAdapterCardObserver() = itemObserver as LiveData<ConsultantListResponce.Data.Consultant>

    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer
        adapter = object : ImmigrationConnectAdapter(R.layout.single_item_connect_immigration, this, list,) {
            override fun onConnectCallBack(userID: Int) {
                commonTaskPerformer.connectClick(userID)
            }

            override fun onChatCallBack(consultant: ConsultantListResponce.Data.Consultant) {
                itemObserver.value=consultant
                //commonTaskPerformer.performAction(ChatActivity::class.java,null,true)
            }

            override fun onCardCallBack(item: ConsultantListResponce.Data.Consultant) {
                commonTaskPerformer.performAction(ConnectDetailsActivity::class.java,null,false)
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
