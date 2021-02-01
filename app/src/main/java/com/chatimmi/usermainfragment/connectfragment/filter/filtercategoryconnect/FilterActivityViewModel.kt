package com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.connectfragment.filter.filtersubcategoryconnect.FiltersubcategoryconnectActivity
import com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup.FilterSubCategoryGroupActivity
import com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup.FilterSubCategoryGroupViewModel

class FilterActivityViewModel : ViewModel() {
    private lateinit var adapter: FIlterActivityAdapter
    private lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer

        adapter = object : FIlterActivityAdapter(R.layout.single_item_filter, this) {
            override fun onLockCallBack() {
                commonTaskPerformer.performAction(FilterSubCategoryGroupActivity::class.java)
            }


        }

    }


    fun getAdapter(): FIlterActivityAdapter? {
        adapter.notifyDataSetChanged();
        return adapter

    }

}