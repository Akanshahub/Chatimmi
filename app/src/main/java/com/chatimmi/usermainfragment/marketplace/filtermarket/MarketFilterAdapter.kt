package com.chatimmi.usermainfragment.marketplace.filtermarket

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemMarketFilterBinding
import com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect.FilterActivityViewModel
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupAdapter

abstract class MarketFilterAdapter (val layout: Int, marketFilterViewModel: MarketFilterViewModel) : RecyclerView.Adapter<MarketFilterAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FilterActivityViewModel? = null
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemMarketFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return MarketFilterAdapter.ViewHolder(binding)

    }

    override fun getItemCount() = 10

    class ViewHolder(binding: SingleItemMarketFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemMarketFilterBinding = binding
    }
    override fun onBindViewHolder(holder: MarketFilterAdapter.ViewHolder, position: Int) {
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