package com.chatimmi.app.utils

import android.widget.PopupMenu

interface CommonTaskPerformer {
    fun <T> performAction(clazz: Class<T>)
    fun  showMsg(msg: String)
    fun dismissDialog()

}