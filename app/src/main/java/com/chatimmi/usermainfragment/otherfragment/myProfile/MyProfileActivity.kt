package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.chatimmi.R
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityMyProfile1Binding
import com.chatimmi.usermainfragment.otherfragment.activity.EditProfileActivity


@Suppress("DEPRECATION")
class MyProfileActivity : BaseActivitykt() {
    private var binding: ActivityMyProfile1Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val listOfFragments = listOf(
                MyChatGroupsFragment.newInstance("1"),
                MyConsultantsFragment.newInstance("2")
        )
        val viewPagerAdapter = MyProfilePagerAdapter(supportFragmentManager, listOfFragments as List<Fragment>, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding!!.pager.adapter = viewPagerAdapter
        binding!!.pager.offscreenPageLimit = 2
        binding!!.tabCategory.setupWithViewPager(binding!!.pager)


        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }

        binding!!.ivEditProfile.setOnClickListener {
            val intent = Intent(this@MyProfileActivity, EditProfileActivity::class.java)
            /*  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
              startActivity(intent)*/
            navigateTo(intent, false)
        }
    }

}