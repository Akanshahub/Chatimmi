package com.chatimmi.usermainfragment.group.filter.filtercategorygroup

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemFilterGroupBinding

abstract class FilterGroupAdapter(val layout: Int, filterGroupViewModel: FilterGroupViewModel) : RecyclerView.Adapter<FilterGroupAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FilterGroupViewModel? = null
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemFilterGroupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return FilterGroupAdapter.ViewHolder(binding)

    }

    override fun getItemCount() = 10

    class ViewHolder(binding: SingleItemFilterGroupBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemFilterGroupBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*      holder.binding.rvCategory.setOnClickListener() {
                  selectedPosition = holder.adapterPosition
                  notifyDataSetChanged()
              }*/
        holder.binding.UKImmigrationCheckbox.setOnClickListener() {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }

        holder.binding.UKImmigrationCheckbox.isChecked = selectedPosition == position
        if (holder.binding.UKImmigrationCheckbox.isChecked) {
            holder.binding.rvCategory.setOnClickListener() {
                onLockCallBack()
            }
        }
     
    }

    abstract fun onLockCallBack()
}

