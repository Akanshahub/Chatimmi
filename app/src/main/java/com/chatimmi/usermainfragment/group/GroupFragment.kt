package com.chatimmi.usermainfragment.group

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.chatimmi.R
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentGroupBinding
import com.chatimmi.usermainfragment.activity.notification.NotificationActivity
import com.chatimmi.usermainfragment.group.immigration.ImmigrationFragment
import com.chatimmi.usermainfragment.group.study.StudyFragment


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GroupFragment : BaseFragment() {
    lateinit var binding: FragmentGroupBinding
    var immigrationFragment= ImmigrationFragment.newInstance("1")
    var studyFragment=  StudyFragment.newInstance("2")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentGroupBinding>(inflater, R.layout.fragment_group, container, false)
        createVm()
        return binding.root
    }

    private fun createVm() {
        val listOfFragments = listOf(
                immigrationFragment,
                studyFragment
               // ImmigrationFragment.newInstance("1"),
        )
        binding.notification.setOnClickListener {
            val intent = Intent(getActivity(), NotificationActivity::class.java)
            getActivity()?.startActivity(intent)
        }
        val viewPagerAdapter = PagerAdapterGroup(childFragmentManager, listOfFragments as List<Fragment>, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
             if(position==0){
                 immigrationFragment.onRefresh()
             }
                if(position==1){
                 studyFragment.onRefresh()
             }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })


        binding.pager.adapter = viewPagerAdapter
        binding.pager.offscreenPageLimit = 2
        binding.tabCategory.setupWithViewPager(binding.pager)
    }
}