package com.chatimmi.helper.joindailong

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.base.BaseActivity
import com.chatimmi.base.BaseBottomDialog
import com.theartofdev.edmodo.cropper.R


class JoinViewModel: ViewModel() {
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer;
    }
    fun onLaterClick() {
        commonTaskPerformer.dismissDialog()
      }

        fun onSubmitClick() {
            commonTaskPerformer.dismissDialog()

        }
    }
