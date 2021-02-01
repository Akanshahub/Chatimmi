package com.chatimmi.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N>() : ViewModel() {
    val isLoading = ObservableBoolean()
    private lateinit var mNavigator: WeakReference<N>
    override fun onCleared() {
        super.onCleared()
    }
    open fun getNavigator(): N? {
        return mNavigator.get()
    }
    open fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }
}
