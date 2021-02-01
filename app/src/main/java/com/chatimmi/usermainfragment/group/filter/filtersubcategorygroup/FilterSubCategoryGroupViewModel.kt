package com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupAdapter

class FilterSubCategoryGroupViewModel : ViewModel() {
    private lateinit var adapter: FilterSubCategoryGroupAdapter

    fun init() {
        adapter =  FilterSubCategoryGroupAdapter(R.layout.single_item_filter_sub_category_group, this)

    }


    fun getAdapter(): FilterSubCategoryGroupAdapter? {
      //  adapter.notifyDataSetChanged();
        return adapter

    }

}