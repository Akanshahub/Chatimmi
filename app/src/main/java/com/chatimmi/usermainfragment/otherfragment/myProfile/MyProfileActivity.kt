package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityMyProfile1Binding
import com.chatimmi.usermainfragment.otherfragment.activity.EditProfileActivity


@Suppress("DEPRECATION")
class MyProfileActivity : BaseActivitykt() {
    private var binding: ActivityMyProfile1Binding? = null
    lateinit var session: Session
    private var viewModel: MyChatGroupImmigrationViewModel? = null
    lateinit var myChatGroupRepository: MyChatGroupRepository
    var myChatGroupsFragment = MyChatGroupsFragment.newInstance("1")
    var myConsultantsFragment = MyConsultantsFragment.newInstance("2")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        myChatGroupRepository = MyChatGroupRepository(this)

        val listOfFragments = listOf(
                myChatGroupsFragment,
                myConsultantsFragment
        )
        val viewPagerAdapter = MyProfilePagerAdapter(supportFragmentManager, listOfFragments as List<Fragment>, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding!!.pager.adapter = viewPagerAdapter
        binding!!.pager.offscreenPageLimit = 2
        binding!!.tabCategory.setupWithViewPager(binding!!.pager)

        binding!!.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    //myChatGroupsFragment.binding.ConnectedCheckbox.isChecked = true

                }
                if (position == 1) {
                    //myConsultantsFragment.binding.checkboxImmigration.isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        session = Session(activity)
        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }
        /* Glide.with(activity).load(session.getUserData()!!.data!!.user_details.avatar).into(binding!!.ivImages)
         binding!!.text.text = session.getUserData()!!.data!!.user_details.full_name
         binding!!.tvEmail.text = session.getUserData()!!.data!!.user_details.email*/
        binding!!.ivEditProfile.setOnClickListener {
            val intent = Intent(this@MyProfileActivity, EditProfileActivity::class.java)
            startActivityForResult(intent, 1)
            //navigateTo(intent, false)
        }
        myChatGroupRepository.callGroupListApi("1", "1")
        myChatGroupRepository.getResponseData().observe(this@MyProfileActivity, androidx.lifecycle.Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GetProfileChatGroupResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data!!.list.size}")

                        Glide.with(activity).load(getData.data!!.userDetails!!.avatar).error(R.drawable.user_placeholder_img).placeholder(R.drawable.user_placeholder_img).into(binding!!.ivImages)
                        binding!!.text.text = getData.data!!.userDetails!!.fullName
                        binding!!.tvEmail.text = getData.data!!.userDetails!!.email


                    }
                    is UIStateManager.Error -> {
                        toastMessage(it.msg, this@MyProfileActivity)
                        // showMsg(it.msg)
                    }
                    is UIStateManager.Loading -> {
                        if (it.shouldShowLoading) {
                            showLoader()
                        } else {
                            hideLoader()
                        }

                    }
                    else -> {

                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 1 && resultCode == RESULT_OK) {

                myChatGroupRepository.callGroupListApi("1", "1")
            }


        } catch (ex: Exception) {
            Toast.makeText(this, ex.toString(),
                    Toast.LENGTH_SHORT).show()
        }
    }
}