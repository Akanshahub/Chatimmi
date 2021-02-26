package com.chatimmi.app.utils

import android.os.Bundle

interface CommonTaskPerformer {
    fun <T> performAction(clazz: Class<T>,bundle: Bundle?,isRequried:Boolean)
    fun  showMsg(msg: String)
    fun dismissDialog()
    fun launchAction()
fun connectClick(userID: Int)

}