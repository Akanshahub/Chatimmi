package com.chatimmi.usermainfragment.activity.notification

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemNotificationBinding

class NotificationAdapter  (val layout: Int, notificationViewModel: NotificationViewModel) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: NotificationViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemNotificationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return NotificationAdapter.ViewHolder(binding)
    }

    override fun getItemCount()=5

    class ViewHolder(binding: SingleItemNotificationBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: SingleItemNotificationBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}