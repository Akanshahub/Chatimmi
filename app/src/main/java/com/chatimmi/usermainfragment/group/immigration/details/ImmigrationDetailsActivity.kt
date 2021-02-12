package com.chatimmi.usermainfragment.group.immigration.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.databinding.ActivityImmigrationDetailsBinding

class ImmigrationDetailsActivity : AppCompatActivity() {
    private var binding: ActivityImmigrationDetailsBinding? = null
    private var viewModel: ImmigrationDetailsViewModel? = null
    var groupName:String = ""
    var categoryName:String = ""
    var subCategoryName:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_immigration_details)

        setupBindings(savedInstanceState)
        groupName= intent.getStringExtra("groupName").toString()
        categoryName= intent.getStringExtra("categoryName").toString()
        subCategoryName= intent.getStringExtra("subCategoryName").toString()
        binding?.name?.text = groupName
        binding?.conoda?.text = categoryName
        binding?.uKImmigration?.text = subCategoryName
        binding?.joinButton?.setOnClickListener(){
            binding?.joinButton!!.visibility=View.GONE
            binding?.tvRequest!!.visibility=View.VISIBLE
        }
    }
    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[ImmigrationDetailsViewModel ::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }
        binding!!.studyModel = viewModel
        binding!!.appBar.ivBtnBack.setOnClickListener {
          onBackPressed()
        }
    }
}


