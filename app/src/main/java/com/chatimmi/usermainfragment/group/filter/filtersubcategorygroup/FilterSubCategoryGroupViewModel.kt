package com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterRepository
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterResponse
import com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup.FilterSubCategoryGroupAdapter.onClick
import java.util.*
import kotlin.collections.ArrayList

class FilterSubCategoryGroupViewModel(var groupFilterRepository: GroupFilterRepository) : ViewModel() {
    private lateinit var adapter: FilterSubCategoryGroupAdapter
    private val list: ArrayList<GroupFilterResponse.Data.Category.Subcategory> = ArrayList()
    fun init(parentCategoryId: List<GroupFilterResponse.Data.Category.Subcategory?>?) {
        adapter = FilterSubCategoryGroupAdapter(R.layout.single_item_filter_sub_category_group, list, object : onClick {
            override fun onItemClick() {


                super.onItemClick()
            }
        })
        //  groupFilterRepository.callGroupCategoryListApi(UUID.randomUUID().toString(), "dsda","2", TimeZone.getDefault().displayName)
    }

    fun clicked(): ArrayList<GroupFilterResponse.Data.Category.Subcategory> {

        return list
    }

    fun getAdapter(): FilterSubCategoryGroupAdapter {
        //  adapter.notifyDataSetChanged();
        return adapter

    }

}