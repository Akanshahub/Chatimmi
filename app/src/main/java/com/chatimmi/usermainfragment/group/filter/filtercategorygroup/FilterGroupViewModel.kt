package com.chatimmi.usermainfragment.group.filter.filtercategorygroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer

class FilterGroupViewModel(var groupFilterRepository: GroupFilterRepository) : ViewModel() {

    private var adapter: FilterGroupAdapter? = null
    private lateinit var commonTaskPerformer: CommonTaskPerformer
    var itemValueObserver = MutableLiveData<GroupFilterResponse.Data.Category>()
    fun getAdapterClickObserver() = itemValueObserver as LiveData<GroupFilterResponse.Data.Category>
    private val list: ArrayList<GroupFilterResponse.Data.Category> = ArrayList()
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer

        adapter = object : FilterGroupAdapter(R.layout.single_item_filter_group, list) {
            override fun onLockCallBack(groupID: GroupFilterResponse.Data.Category) {
                itemValueObserver.value = groupID
            }

        }
        apiCalling()
    }
fun apiCalling(){
    groupFilterRepository.callGroupCategoryListApi()
}
    public fun clearList() {
        list.clear()
        adapter?.notifyDataSetChanged()
    }
    fun getAdapter(): FilterGroupAdapter? {
        return adapter
    }

    fun getResult(categoryId1: String, subCategoryId1: String) {

    }

    fun updateList(finalPosition: Int, filter: Int, list: ArrayList<GroupFilterResponse.Data.Category.Subcategory>) {
        this.list[finalPosition].count = filter
        this.list[finalPosition].subcategories = list
        getAdapter()!!.notifyDataSetChanged()
    }


}