package com.chatimmi.usermainfragment.group.immigration


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.CommonTaskPerformer
import java.util.*
import kotlin.collections.ArrayList

class GroupViewModel(val immigrationGroupRepositary: ImmigrationGroupRepositary) : ViewModel() {
    private var groupAdapter: GroupAdapter? = null
    open var groupListResponse: LiveData<GroupListResponse.Data.Group?>? = null
    private val list: ArrayList<GroupListResponse.Data.Group> = ArrayList()
    var list2: ArrayList<GroupListResponse.Data.Group> = ArrayList()

    var itemObserver = MutableLiveData<GroupListResponse.Data.Group>()
    var itemClickObserver = MutableLiveData<Boolean>()
    var itemValueObserver = MutableLiveData<GroupListResponse.Data.Group>()
    var itemViewObserver = MutableLiveData<Boolean>()
    fun getAdapterCardObserver() = itemObserver as LiveData<GroupListResponse.Data.Group>
    fun getViewObserver() = itemViewObserver as LiveData<Boolean>
    fun getAdapterClickObserver() = itemValueObserver as LiveData<GroupListResponse.Data.Group>
    lateinit var commonTaskPerformer: CommonTaskPerformer


    fun init(commonTaskPerformer: CommonTaskPerformer, session: Session) {
        this.commonTaskPerformer = commonTaskPerformer
        Log.d("bnjnknk", "setupBindings:init")






        groupAdapter = object : GroupAdapter(R.layout.single_item_grouplist, list) {

            override fun onitemBack(groupID: GroupListResponse.Data.Group) {
                itemValueObserver.value = groupID
            }

            override fun onCardCallBack(groupID: GroupListResponse.Data.Group) {
                itemObserver.value = groupID
            }

            override fun onLockCallBack() {
                //itemClickObserver.value=true
            }

            override fun searchingResult(isFound: Boolean) {
                if (isFound) {
                    itemViewObserver.value = true
                } else {
                    itemViewObserver.value = true
                }
            }

        }
        fetchUsers()
    }

    public fun clearList() {
        list.clear()
        groupAdapter?.notifyDataSetChanged()
    }

    fun sendData(categoryId: String, subCategoryId: String, groupScope: String) {
        immigrationGroupRepositary.callGroupListApi(UUID.randomUUID().toString(), "dsda", "2", TimeZone.getDefault().displayName, "1", categoryId, subCategoryId, "")
    }

    public fun fetchUsers() {
        immigrationGroupRepositary.callGroupListApi(UUID.randomUUID().toString(), "dsda", "2", TimeZone.getDefault().displayName, "1", "", "", "")
    }

    fun getAdapter(): GroupAdapter? {
        Log.d("bnjnknk", "setupBindings:getAdapter")
        return groupAdapter
    }

    fun getLoginResponseLiveData(): LiveData<GroupListResponse.Data.Group?>? {
        return groupListResponse
    }
}