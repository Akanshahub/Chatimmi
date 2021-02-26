package com.chatimmi.usermainfragment.marketplace.filtermarket

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.marketplace.filtersubcategory.MarketFilterSubCategoryActivity

class MarketFilterViewModel:ViewModel() {
    private lateinit var adapter: MarketFilterAdapter
    private lateinit var commonTaskPerformer: CommonTaskPerformer

    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer
        adapter =  object:MarketFilterAdapter(R.layout.single_item_market_filter, this){
            override fun onLockCallBack() {
                commonTaskPerformer.performAction(MarketFilterSubCategoryActivity::class.java,null,false)
            }


        }
    }


    fun getAdapter(): MarketFilterAdapter? {
        adapter.notifyDataSetChanged();
        return adapter
    }
}