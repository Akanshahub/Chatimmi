package com.chatimmi.usermainfragment.marketplace.filtermarket

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityMarketFilterBinding

@Suppress("DEPRECATION")
class MarketFilterActivity : BaseActivitykt(), CommonTaskPerformer {
    private var binding: ActivityMarketFilterBinding? = null
    private var viewModel: MarketFilterViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_market_filter)
        setupBindings(savedInstanceState)
        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding!!.checkbox.setOnClickListener {
            binding!!.checkbox.isChecked = true
            binding!!.ConnectedCheckbox.isChecked = false


        }
        binding!!.ConnectedCheckbox.setOnClickListener {
            binding!!.checkbox.isChecked = false
            binding!!.ConnectedCheckbox.isChecked = true


        }
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[MarketFilterViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
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