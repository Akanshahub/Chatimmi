package com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityFilterBinding

@Suppress("DEPRECATION")
class FilterActivity : BaseActivitykt(), CommonTaskPerformer {
    private var binding: ActivityFilterBinding? = null
    private var viewModel: FilterActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        setupBindings(savedInstanceState)
        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding!!.checkbox1.setOnClickListener {
            binding!!.checkbox1.isChecked = true
            binding!!.ConnectedCheckbox.isChecked = false


        }
        binding!!.ConnectedCheckbox.setOnClickListener {
            binding!!.checkbox1.isChecked = false
            binding!!.ConnectedCheckbox.isChecked = true


        }
     /*   binding!!.tvFilter.setOnClickListener() {
            showToast("Under Development")
        }*/
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[FilterActivityViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
        }

        binding!!.model = viewModel
    }

    override fun <T> performAction(clazz: Class<T>) {
        Intent(this, clazz).apply {
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {

    }

    override fun dismissDialog() {

    }

    override fun launchAction() {

    }

    override fun connectClick(userID: Int) {

    }


}

