package com.chatimmi.app.utils

import android.R
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout

class ProgressBarUtil(private val mContext: Context) {
    private val mProgressBar: ProgressBar
    fun show() {
        mProgressBar.visibility = View.VISIBLE
    }

    fun hide() {
        mProgressBar.visibility = View.INVISIBLE
    }

    init {
        val layout = (mContext as Activity).findViewById<View>(R.id.content).rootView as ViewGroup
        mProgressBar = ProgressBar(mContext, null, R.attr.progressBarStyleLarge)
        mProgressBar.isIndeterminate = true
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
        val rl = RelativeLayout(mContext)
        rl.gravity = Gravity.CENTER
        rl.addView(mProgressBar)
        layout.addView(rl, params)
        hide()
    }
}