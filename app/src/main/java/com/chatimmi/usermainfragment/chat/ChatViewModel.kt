package com.chatimmi.usermainfragment.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt

class ChatViewModel(var chatRepository: ChatRepository)  : ViewModel(){
    private lateinit var adapter: ChatAdapter

    var itemObserver = MutableLiveData<ChatHistoryResponse.Data.History>()
    private val list: ArrayList<ChatHistoryResponse.Data.History> = ArrayList()
     var chatFragment=BaseActivitykt()

    fun getAdapterCardObserver() = itemObserver as LiveData<ChatHistoryResponse.Data.History>

    fun init() {
        apiCalling()
        adapter = object : ChatAdapter(R.layout.single_chat_item, list,chatFragment){
            override fun onLockCallBack(consultant: ChatHistoryResponse.Data.History) {
                itemObserver.value=consultant
            }
        }

    }

    fun apiCalling(){
      chatRepository.callChatHistoryListApi()
    }
    fun getAdapter(): ChatAdapter {
        return adapter
    }
}
