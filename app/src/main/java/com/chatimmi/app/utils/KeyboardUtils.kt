package com.chatimmi.app.utils

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardUtils {
    fun hideKeyboard(context: Context, windowToken: IBinder?) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    fun showKeyboard(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.toggleSoftInputFromWindow(view.applicationWindowToken,
                InputMethodManager.SHOW_FORCED, 0)
    }

    companion object {
        @JvmStatic
        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}