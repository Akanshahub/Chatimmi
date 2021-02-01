package com.chatimmi.usermainfragment.otherfragment.myProfile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

@Suppress("DEPRECATION")
class MyProfilePagerAdapter(fragmentManager: FragmentManager, private val listFragment: List<Fragment>,behaviour : Int)
    : FragmentStatePagerAdapter(fragmentManager,behaviour) {
    private val tabNameList = listOf("My Chat Groups", "My Consultants")
    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return tabNameList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabNameList[position]
    }


}