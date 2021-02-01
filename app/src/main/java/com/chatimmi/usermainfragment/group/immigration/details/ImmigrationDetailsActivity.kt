package com.chatimmi.usermainfragment.group.immigration.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.databinding.ActivityImmigrationDetailsBinding

class ImmigrationDetailsActivity : AppCompatActivity() {
    private var binding: ActivityImmigrationDetailsBinding? = null
    private var viewModel: ImmigrationDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_immigration_details)
        setupBindings(savedInstanceState)
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


