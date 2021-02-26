package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.ActivityMyProfile1Binding
import com.chatimmi.usermainfragment.otherfragment.activity.EditProfileActivity


@Suppress("DEPRECATION")
class MyProfileActivity : BaseActivitykt() {
    private var binding: ActivityMyProfile1Binding? = null
    lateinit var session: Session
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
        myChatGroupRepository = MyChatGroupRepository(activity)
        val listOfFragments = listOf(
                myChatGroupsFragment,
                myConsultantsFragment
        )
        val viewPagerAdapter = MyProfilePagerAdapter(supportFragmentManager, listOfFragments as List<Fragment>, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding!!.pager.adapter = viewPagerAdapter
        binding!!.pager.offscreenPageLimit = 2
        binding!!.tabCategory.setupWithViewPager(binding!!.pager)
/*        myChatGroupRepository.getResponseData().observe(this@MyProfileActivity, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GetProfileChatGroupResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data!!.list.size}")

                        Glide.with(activity).load(getData.data!!.userDetails!!.avatar).into(binding!!.ivImages)
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
        })*/
        binding!!.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    myChatGroupsFragment.binding.ConnectedCheckbox.isChecked = true

                }
                if (position == 1) {
                    myConsultantsFragment.binding.checkboxImmigration.isChecked = true
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        session = Session(activity)
        binding!!.backButton.setOnClickListener {
            onBackPressed()
        }
        Glide.with(activity).load(session.getUserData()!!.data!!.user_details.avatar).into(binding!!.ivImages)
        binding!!.text.text = session.getUserData()!!.data!!.user_details.full_name
        binding!!.tvEmail.text = session.getUserData()!!.data!!.user_details.email
        binding!!.ivEditProfile.setOnClickListener {
            val intent = Intent(this@MyProfileActivity, EditProfileActivity::class.java)
            /*  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
              startActivity(intent)*/
            navigateTo(intent, false)
        }
    }

}