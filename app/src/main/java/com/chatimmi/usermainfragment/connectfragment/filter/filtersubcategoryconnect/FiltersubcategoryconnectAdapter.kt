package com.chatimmi.usermainfragment.connectfragment.filter.filtersubcategoryconnect

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemFilterBinding
import com.chatimmi.databinding.SingleItemFilterConnectSubcategoryBinding
import com.chatimmi.databinding.SingleItemFilterSubCategoryGroupBinding
import com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect.FIlterActivityAdapter
import com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect.FilterActivityViewModel

class FiltersubcategoryconnectAdapter(val layout: Int, filtersubcategoryconnectViewModel: FiltersubcategoryconnectViewModel) : RecyclerView.Adapter<FiltersubcategoryconnectAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FiltersubcategoryconnectViewModel? = null
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemFilterConnectSubcategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return FiltersubcategoryconnectAdapter.ViewHolder(binding)

    }

    override fun getItemCount() = 10

    class ViewHolder(binding: SingleItemFilterConnectSubcategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemFilterConnectSubcategoryBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    /*    holder.binding.rvCategory.setOnClickListener() {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
        holder.binding.UKImmigrationCheckbox.setOnClickListener() {
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }*/
        holder.binding.UKImmigrationCheckbox.isChecked = selectedPosition == position
    }
}

