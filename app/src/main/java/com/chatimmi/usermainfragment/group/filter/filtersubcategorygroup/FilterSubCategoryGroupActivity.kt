package com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseActivity
import com.chatimmi.databinding.ActivityFilterGroupBinding
import com.chatimmi.databinding.ActivityFilterSubCategoryGroupBinding
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupViewModel

@Suppress("DEPRECATION")
class FilterSubCategoryGroupActivity : BaseActivity() {
    private var binding: ActivityFilterSubCategoryGroupBinding? = null

    /*   private var viewModel: ConnectDetailsViewModel? = null*/
    private var viewModel: FilterSubCategoryGroupViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_sub_category_group)
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
        viewModel = ViewModelProviders.of(this)[FilterSubCategoryGroupViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }

        binding!!.model = viewModel
    }
}