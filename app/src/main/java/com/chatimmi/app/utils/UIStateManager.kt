package com.chatimmi.app.utils

sealed class UIStateManager {
    data class Success<T>(val data: T) : UIStateManager()
    data class Error(val msg: String) : UIStateManager()
    data class Loading(val shouldShowLoading: Boolean) : UIStateManager()
}