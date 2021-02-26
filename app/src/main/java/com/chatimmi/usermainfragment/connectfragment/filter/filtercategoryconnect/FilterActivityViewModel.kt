package com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup.FilterSubCategoryGroupActivity

class FilterActivityViewModel : ViewModel() {
    private lateinit var adapter: FIlterActivityAdapter
    private lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer

        adapter = object : FIlterActivityAdapter(R.layout.single_item_filter, this) {
            override fun onLockCallBack() {
                commonTaskPerformer.performAction(FilterSubCategoryGroupActivity::class.java,null,false)
            }


        }

    }


    fun getAdapter(): FIlterActivityAdapter? {
        adapter.notifyDataSetChanged();
        return adapter

    }

}