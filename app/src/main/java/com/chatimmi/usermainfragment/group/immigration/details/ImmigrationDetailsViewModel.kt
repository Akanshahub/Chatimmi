package com.chatimmi.usermainfragment.group.immigration.details

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import java.util.*

class ImmigrationDetailsViewModel(val immigrationDetailsRepositary: ImmigrationDetailsRepositary) : ViewModel() {
    private lateinit var adapter: ImmigrationDetailsAdapter

    // private var groupId = GroupListResponse.Data.Group()
    lateinit var group: String
    lateinit var commonTaskPerformer: CommonTaskPerformer
    private val list: ArrayList<ImmigrationDetailsResponse.Data.GroupMember> = ArrayList()

    //  private var viewModel: ImageViewModel? = null
    fun init(groupId: String, commonTaskPerformer: CommonTaskPerformer) {
        this.group = groupId
        this.commonTaskPerformer = commonTaskPerformer
        adapter = object : ImmigrationDetailsAdapter(R.layout.single_item_join_member, list) {
            override fun onConnectCallBack(userID: Int) {
                commonTaskPerformer.connectClick(userID)
            }
        }



        fetchUsers()
        immigrationDetailsRepositary.callConnectGroupApi(group)
    }


    fun fetchUsers() {
        immigrationDetailsRepositary.callGroupDetailResponse(group)
    }

    fun getAdapter(): ImmigrationDetailsAdapter? {
        return adapter
    }

    fun setConnectConsultantAPI(userId: String) {
        immigrationDetailsRepositary.callConnectApi(userId)
    }

}