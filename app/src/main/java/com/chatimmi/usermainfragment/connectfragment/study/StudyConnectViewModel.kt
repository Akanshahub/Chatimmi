package com.chatimmi.usermainfragment.connectfragment.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.connectfragment.details.ConnectDetailsActivity
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ConsultantListResponce
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ImmigrationConnectRepositary
import java.util.*

class StudyConnectViewModel(val repo: ImmigrationConnectRepositary)  : ViewModel(){
    private val list: ArrayList<ConsultantListResponce.Data.Consultant> = ArrayList()
    lateinit var commonTaskPerformer: CommonTaskPerformer
  private lateinit var adapter: StudyConnectAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
        fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
        fun init(commonTaskPerformer: CommonTaskPerformer) {
            this.commonTaskPerformer=commonTaskPerformer
            adapter = object : StudyConnectAdapter(R.layout.single_item_study_connect, this,list){
                override fun onConnectCall() {

                }


                override fun onConnectCallBack(userID: Int) {

                    commonTaskPerformer.connectClick(userID)
                }

                override fun onCardCallBack(item: ConsultantListResponce.Data.Consultant) {
                    commonTaskPerformer.performAction(ConnectDetailsActivity::class.java)
                }
            }
        }
        fun getAdapter(): StudyConnectAdapter? {
            return adapter
        }

    fun callConsultantConnectListApi() {
        repo.callConsultantListApi("3")
    }

    fun setConnectConsultantAPI(userId:String) {
        repo.callConnectApi(userId)
    }
    }
