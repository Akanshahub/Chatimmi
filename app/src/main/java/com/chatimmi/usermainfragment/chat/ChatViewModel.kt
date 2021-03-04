package com.chatimmi.usermainfragment.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R

class ChatViewModel  : ViewModel(){
    private lateinit var adapter: ChatAdapter
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>


    fun init() {
        adapter = object : ChatAdapter(R.layout.single_chat_item, this){
            override fun onLockCallBack() {
                itemClickObserver.value = true
            }
        }
    }
    fun getAdapter(): ChatAdapter {
        return adapter
    }
}
