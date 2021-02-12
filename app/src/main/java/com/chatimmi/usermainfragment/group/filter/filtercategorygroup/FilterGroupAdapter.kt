package com.chatimmi.usermainfragment.group.filter.filtercategorygroup

import CustomCategoryList
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemFilterGroupBinding
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse

abstract class FilterGroupAdapter(val layout: Int, public var categoryListItem: ArrayList<GroupFilterResponse.Data.Category>) : RecyclerView.Adapter<FilterGroupAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FilterGroupViewModel? = null
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemFilterGroupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return FilterGroupAdapter.ViewHolder(binding)

    }


    override fun getItemCount(): Int = categoryListItem.size
    class ViewHolder(binding: SingleItemFilterGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemFilterGroupBinding = binding
        init {


        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val categoryListItem: GroupFilterResponse.Data.Category = categoryListItem[position]
        holder.binding.tvName.text = categoryListItem.name

        holder.binding.UKImmigrationCheckbox.isChecked = categoryListItem.isSelect!!

        holder.binding.UKImmigrationCheckbox.isClickable = false
        if (categoryListItem.count!! == 0 ) {
            holder.binding.tick.visibility= View.GONE
      } else {
          //  holder.binding.UKImmigrationCheckbox.isChecked=true
           holder.binding.tick.visibility= View.VISIBLE
            holder.binding.tick.text = categoryListItem.count!!.toString()
        }
        holder.binding.UKImmigrationCheckbox.isChecked = categoryListItem.count!!>0

       holder.binding.rvCategory.setOnClickListener() {
           onLockCallBack(categoryListItem)
        }


/*        holder.binding.UKImmigrationCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            var flag = false
            for(i in 0 until FilterGroupActivity.customCategoryList.size){
                val getList = FilterGroupActivity.customCategoryList[i]
                if(getList.categoryID ==  categoryListItem.categoryID){
                    flag = true
                    getList.isSelected = isChecked
                }
                if(flag){
                    break
                }
            }
            if(!flag){
                val customCat = CustomCategoryList(categoryListItem.categoryID!!,true)
                FilterGroupActivity.customCategoryList.add(customCat)
            }
        }*/
    }


    fun addData(list: List<GroupFilterResponse.Data.Category>) {
        categoryListItem.addAll(list)
    }

    abstract fun onLockCallBack(groupID: GroupFilterResponse.Data.Category)
}

