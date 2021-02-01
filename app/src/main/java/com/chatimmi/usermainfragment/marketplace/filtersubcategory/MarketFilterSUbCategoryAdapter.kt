package com.chatimmi.usermainfragment.marketplace.filtersubcategory

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemFilterConnectSubcategoryBinding
import com.chatimmi.databinding.SingleItemFilterMarketplaceSubcategoryBinding
import com.chatimmi.usermainfragment.connectfragment.filter.filtersubcategoryconnect.FiltersubcategoryconnectAdapter
import com.chatimmi.usermainfragment.connectfragment.filter.filtersubcategoryconnect.FiltersubcategoryconnectViewModel

class MarketFilterSUbCategoryAdapter (val layout: Int, marketFilterSubCategoryViewModel: MarketFilterSubCategoryViewModel) : RecyclerView.Adapter<MarketFilterSUbCategoryAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FiltersubcategoryconnectViewModel? = null
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemFilterMarketplaceSubcategoryBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return MarketFilterSUbCategoryAdapter.ViewHolder(binding)

    }

    override fun getItemCount() = 10

    class ViewHolder(binding: SingleItemFilterMarketplaceSubcategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemFilterMarketplaceSubcategoryBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
  /*      holder.binding.rvCategory.setOnClickListener() {
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

