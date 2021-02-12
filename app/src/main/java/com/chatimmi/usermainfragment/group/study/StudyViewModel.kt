package com.chatimmi.usermainfragment.group.study

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse
import com.chatimmi.usermainfragment.group.immigration.ImmigrationGroupRepositary
import java.util.*
import kotlin.collections.ArrayList

class StudyViewModel(val immigrationGroupRepositary: ImmigrationGroupRepositary): ViewModel(){
    private  var adapter: StudyAdapter?=null
    var itemClickObserver = MutableLiveData<Boolean>()
    open var groupListResponse: LiveData<GroupListResponse.Data.Group?>? = null
    private  val list:ArrayList<GroupListResponse.Data.Group> = ArrayList()
       // fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
       var itemValueObserver = MutableLiveData<GroupListResponse.Data.Group>()
       var itemObserver = MutableLiveData<GroupListResponse.Data.Group>()
       fun getAdapterClickObserver() = itemValueObserver as LiveData<GroupListResponse.Data.Group>
       fun getAdapterCardObserver() = itemObserver as LiveData<GroupListResponse.Data.Group>
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer,session:Session) {
        this.commonTaskPerformer = commonTaskPerformer;


        adapter = object : StudyAdapter(R.layout.single_item_study,list){
            override fun onitemBack(groupID: GroupListResponse.Data.Group) {
                itemValueObserver.value=groupID
            }
            override fun onLockCallBack() {
               // itemClickObserver.value=true
            }
            override fun onCardCallBack(groupID: GroupListResponse.Data.Group) {
                itemObserver.value=groupID
            }

        }
        fetchUsers()
    }
    public fun clearList(){
        list.clear()
        getAdapter()?.notifyDataSetChanged()
    }
    public fun fetchUsers() {
        immigrationGroupRepositary.callGroupListApi(UUID.randomUUID().toString(), "dsda","2", TimeZone.getDefault().displayName,"2","","","" )
    }
        fun getAdapter(): StudyAdapter? {
            return adapter
        }
    fun sendData(categoryId: String, subCategoryId: String, groupScope: String) {
        immigrationGroupRepositary.callGroupListApi(UUID.randomUUID().toString(), "dsda", "2", TimeZone.getDefault().displayName, "1", categoryId, subCategoryId,  groupScope)
    }
    fun getLoginResponseLiveData(): LiveData<GroupListResponse.Data.Group?>? {
        return groupListResponse
    }

    }

