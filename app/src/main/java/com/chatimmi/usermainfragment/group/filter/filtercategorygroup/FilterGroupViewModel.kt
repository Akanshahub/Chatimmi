package com.chatimmi.usermainfragment.group.filter.filtercategorygroup

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.connectfragment.study.StudyConnectAdapter
import com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup.FilterSubCategoryGroupActivity
import com.chatimmi.usermainfragment.group.immigration.details.ImmigrationDetailsActivity

class FilterGroupViewModel:ViewModel() {
    private lateinit var adapter: FilterGroupAdapter
    private lateinit var commonTaskPerformer: CommonTaskPerformer

    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer
        adapter =  object :FilterGroupAdapter(R.layout.single_item_filter_group, this){
            override fun onLockCallBack() {
               // commonTaskPerformer.showMsg("dfdfdcdxzc")
                commonTaskPerformer.performAction(FilterSubCategoryGroupActivity::class.java)
            }

        }

    }
    fun getAdapter(): FilterGroupAdapter? {
        return adapter
    }
}