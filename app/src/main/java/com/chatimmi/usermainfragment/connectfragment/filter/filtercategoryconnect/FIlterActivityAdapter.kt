package com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemFilterBinding
import com.chatimmi.usermainfragment.activity.notification.NotificationActivity
import com.chatimmi.usermainfragment.connectfragment.filter.filtersubcategoryconnect.FiltersubcategoryconnectActivity
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupAdapter


abstract class FIlterActivityAdapter(val layout: Int, filterActivityViewModel: FilterActivityViewModel) : RecyclerView.Adapter<FIlterActivityAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: FilterActivityViewModel? = null
    private var selectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return FIlterActivityAdapter.ViewHolder(binding)

    }

    override fun getItemCount() = 10

    class ViewHolder(binding: SingleItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemFilterBinding = binding
    }

    override fun onBindViewHolder(holder: FIlterActivityAdapter.ViewHolder, position: Int) {
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

