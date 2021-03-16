package com.chatimmi.usermainfragment.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ChatViewModelFactory (private val chatRepository: ChatRepository) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatViewModel::class.java)){
            return ChatViewModel(chatRepository) as T
        }else{
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}