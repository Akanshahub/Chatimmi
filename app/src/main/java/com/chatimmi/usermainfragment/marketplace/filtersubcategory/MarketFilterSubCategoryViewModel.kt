package com.chatimmi.usermainfragment.marketplace.filtersubcategory

import androidx.lifecycle.ViewModel
import com.chatimmi.R

class MarketFilterSubCategoryViewModel : ViewModel() {
    private lateinit var adapter: MarketFilterSUbCategoryAdapter

    fun init() {


        adapter =  MarketFilterSUbCategoryAdapter(R.layout.single_item_filter_marketplace_subcategory, this)

    }


    fun getAdapter(): MarketFilterSUbCategoryAdapter? {
        return adapter

    }

}
