package com.chatimmi.usermainfragment.connectfragment.filter.filtersubcategoryconnect

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityFilterSubCategoryConnectBinding

@Suppress("DEPRECATION")
class FiltersubcategoryconnectActivity: BaseActivitykt(), CommonTaskPerformer {
    private var binding: ActivityFilterSubCategoryConnectBinding? = null
    private var viewModel: FiltersubcategoryconnectViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_sub_category_connect)
        setupBindings(savedInstanceState)
        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        /*   binding!!.tvFilter.setOnClickListener() {
               showToast("Under Development")
           }*/
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[FiltersubcategoryconnectViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }

        binding!!.model = viewModel
    }

    override fun <T> performAction(clazz: Class<T>, bundle: Bundle?, isRequried: Boolean) {

        Intent(this, clazz,).apply {
            if(isRequried){
                this.putExtras(bundle!!)
            }
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

