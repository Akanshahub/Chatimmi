package com.chatimmi.retrofitnetwork

interface BaseInterface {

    fun onShowBaseLoader()
    fun onHideBaseLoader()
    fun onError(errorMessage: String)
}