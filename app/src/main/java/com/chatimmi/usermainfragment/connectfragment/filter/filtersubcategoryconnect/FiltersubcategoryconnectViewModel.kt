package com.chatimmi.usermainfragment.connectfragment.filter.filtersubcategoryconnect

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect.FIlterActivityAdapter

class FiltersubcategoryconnectViewModel: ViewModel() {
    private lateinit var adapter: FiltersubcategoryconnectAdapter

    fun init() {


        adapter =  FiltersubcategoryconnectAdapter(R.layout.single_item_filter_connect_subcategory, this)

    }


    fun getAdapter(): FiltersubcategoryconnectAdapter? {
        return adapter

    }

}