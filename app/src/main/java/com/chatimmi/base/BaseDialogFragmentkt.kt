package com.chatimmi.base

import android.content.Context
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment

class BaseDialogFragmentkt: DialogFragment() {
    var mBaseActivity:BaseActivity?=null
    private var isFullScreen = false
    private var style = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //this.context = context
        mBaseActivity = context as BaseActivity
        super.onAttach(context)
    }

    protected fun setFullScreen(isFullScreen: Boolean) {
        this.isFullScreen = isFullScreen
    }

    protected fun setStyle(@StyleRes style: Int) {
        this.style = style
    }
}