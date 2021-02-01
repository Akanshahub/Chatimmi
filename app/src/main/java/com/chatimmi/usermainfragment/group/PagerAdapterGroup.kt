package com.chatimmi.usermainfragment.group

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PagerAdapterGroup(fragmentManager: FragmentManager, private val listFragment: List<Fragment>, behaviour : Int)
    : FragmentStatePagerAdapter(fragmentManager,behaviour) {
    private val tabNameList = listOf("Immigration", "Study")
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