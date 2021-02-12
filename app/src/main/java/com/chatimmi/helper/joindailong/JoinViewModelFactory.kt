package com.chatimmi.helper.joindailong

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chatimmi.repository.ForgetPasswordRepository
import com.chatimmi.viewmodel.ForgetPasswordViewModel
import java.lang.IllegalArgumentException



@Suppress("UNCHECKED_CAST")
class JoinViewModelFactory(private val joinRespository: JoinRespository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JoinViewModel::class.java)) {
            return JoinViewModel(joinRespository) as T
        } else {
            throw IllegalArgumentException("Modal Class does not found")
        }
    }
}