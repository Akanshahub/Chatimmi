package com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemFilterGroupBinding
import com.chatimmi.databinding.SingleItemFilterSubCategoryGroupBinding
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupAdapter
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupViewModel

class FilterSubCategoryGroupAdapter (val layout: Int, filterSubCategoryGroupViewModel: FilterSubCategoryGroupViewModel) : RecyclerView.Adapter<FilterSubCategoryGroupAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FilterSubCategoryGroupViewModel? = null
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemFilterSubCategoryGroupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return FilterSubCategoryGroupAdapter.ViewHolder(binding)

    }

    override fun getItemCount() = 10

    class ViewHolder(binding: SingleItemFilterSubCategoryGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemFilterSubCategoryGroupBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
/*        holder.binding.rvCategory.setOnClickListener() {
            selectedPosition = holder.adapterPosition

            notifyDataSetChanged()
        }*/
/*        holder.binding.UKImmigrationCheckbox.setOnClickListener() {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }*/

        holder.binding.UKImmigrationCheckbox.isChecked = selectedPosition == position


    }
}

