package com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemFilterSubCategoryGroupBinding
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterResponse

class FilterSubCategoryGroupAdapter(val layout: Int,
                                    private var groupListItem: ArrayList<GroupFilterResponse.Data.Category.Subcategory>,
                                    var click: onClick) : RecyclerView.Adapter<FilterSubCategoryGroupAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FilterSubCategoryGroupViewModel? = null
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemFilterSubCategoryGroupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return FilterSubCategoryGroupAdapter.ViewHolder(binding)

    }

    override fun getItemCount(): Int = groupListItem.size


    class ViewHolder(binding: SingleItemFilterSubCategoryGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemFilterSubCategoryGroupBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryListItem: GroupFilterResponse.Data.Category.Subcategory = groupListItem[position]
        holder.binding.tvSubCategory.text = categoryListItem.name


        holder.binding.rvCategory.setOnClickListener {

            holder.binding.UKImmigrationCheckbox.isChecked = !holder.binding.UKImmigrationCheckbox.isChecked
            categoryListItem.isselected = categoryListItem.isselected != true
            notifyDataSetChanged()

        }


        if (categoryListItem.isselected!!) {
            holder.binding.UKImmigrationCheckbox.isChecked  = true
        } else {
            holder.binding.UKImmigrationCheckbox.isChecked  = false
        }
    }

    fun addData(list: List<GroupFilterResponse.Data.Category.Subcategory>) {
        groupListItem.addAll(list)
    }

    interface onClick {
        fun onItemClick() {

        }
    }


}

