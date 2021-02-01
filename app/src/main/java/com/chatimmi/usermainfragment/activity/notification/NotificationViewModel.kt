package com.chatimmi.usermainfragment.activity.notification
import androidx.lifecycle.ViewModel
import com.chatimmi.R


class NotificationViewModel : ViewModel(){
    private lateinit var adapter: NotificationAdapter

    fun init() {
        adapter = NotificationAdapter(R.layout.single_item_notification, this)
    }

    fun getAdapter(): NotificationAdapter? {
        return adapter
    }

}
