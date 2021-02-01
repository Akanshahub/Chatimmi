package com.chatimmi.usermainfragment.group.filter.filtercategorygroup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.base.BaseActivity
import com.chatimmi.databinding.ActivityFilterGroupBinding

@Suppress("DEPRECATION")
class FilterGroupActivity : BaseActivity() ,CommonTaskPerformer{
    private var binding: ActivityFilterGroupBinding? = null
 /*   private var viewModel: ConnectDetailsViewModel? = null*/
    private var viewModel: FilterGroupViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_group)
        setupBindings(savedInstanceState)
        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[FilterGroupViewModel::class.java]
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
}