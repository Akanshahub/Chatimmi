package com.chatimmi.views

import android.os.Bundle
import com.chatimmi.R
import com.chatimmi.base.BaseActivity


class DummyActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_selection)
    }
}