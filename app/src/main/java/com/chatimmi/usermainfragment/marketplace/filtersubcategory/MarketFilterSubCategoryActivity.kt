package com.chatimmi.usermainfragment.marketplace.filtersubcategory

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseActivity
import com.chatimmi.databinding.ActivityMarketFilterBinding
import com.chatimmi.databinding.ActivityMarketFilterSubCateogryBinding
import com.chatimmi.usermainfragment.marketplace.filtermarket.MarketFilterViewModel

@Suppress("DEPRECATION")
class MarketFilterSubCategoryActivity : BaseActivity() {
    private var binding: ActivityMarketFilterSubCateogryBinding? = null
    private var viewModel: MarketFilterSubCategoryViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_market_filter_sub_cateogry)
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
        viewModel = ViewModelProviders.of(this)[MarketFilterSubCategoryViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }

        binding!!.model = viewModel
    }

}