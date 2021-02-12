package com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.base.BaseActivity
import com.chatimmi.databinding.ActivityFilterSubCategoryGroupBinding
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupActivity
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterRepository
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.GroupFilterResponse


@Suppress("DEPRECATION")
class FilterSubCategoryGroupActivity : BaseActivity() {
    private var binding: ActivityFilterSubCategoryGroupBinding? = null
    lateinit var groupFilterRepository: GroupFilterRepository

    private var viewModel: FilterSubCategoryGroupViewModel? = null
    var parentCategoryId = GroupFilterResponse.Data.Category()
    var list = ArrayList<GroupFilterResponse.Data.Category.Subcategory>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_sub_category_group)

        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        parentCategoryId = intent.getParcelableExtra<GroupFilterResponse.Data.Category>("parent_category")!!
        binding!!.tvTitle11.text = parentCategoryId.name

        setupBindings(savedInstanceState)
    }

    private fun setupBindings(savedInstanceState: Bundle?) {

        groupFilterRepository = GroupFilterRepository(activity)
        val factory = GroupSubCategoryViewModelFactory(groupFilterRepository)
        viewModel = ViewModelProviders.of(this, factory)[FilterSubCategoryGroupViewModel::class.java]

       if (savedInstanceState == null) {
            viewModel?.init(parentCategoryId.subcategories)
            viewModel?.getAdapter()?.let {
              binding!!.rvMain.adapter = viewModel?.getAdapter()
               viewModel?.getAdapter()!!.addData(parentCategoryId.subcategories)
              viewModel?.getAdapter()!!.notifyDataSetChanged()
            }
        }

        binding!!.model = viewModel
        binding!!.btnSignup.setOnClickListener {
            val intent = Intent(this, FilterGroupActivity::class.java)
            intent.putParcelableArrayListExtra("list", viewModel!!.clicked())
            intent.putExtra("position",parentCategoryId)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}