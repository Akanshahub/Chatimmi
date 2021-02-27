
package com.chatimmi.usermainfragment.connectfragment

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
import com.chatimmi.databinding.FragmentConnectBinding
import com.chatimmi.usermainfragment.activity.notification.NotificationActivity
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ImmigrationConnectFragment
import com.chatimmi.usermainfragment.connectfragment.study.StudyConnectFragment


class ConnectFragment : BaseFragment() {
    lateinit var binding: FragmentConnectBinding
var immigrationConnectFragment=ImmigrationConnectFragment.newInstance("1")
    var studyConnectFragment=StudyConnectFragment.newInstance("2")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentConnectBinding>(inflater, R.layout.fragment_connect, container, false)
        createVm()
        binding.notification.setOnClickListener {
            /*Toast.makeText(getActivity(), "Under Development",  Toast.LENGTH_LONG).show();*/
            val intent = Intent(getActivity(), NotificationActivity::class.java)
            //listener?.onClick
            intent.putExtra("dd", "ff")
            getActivity()?.startActivity(intent)
        }

        return binding.root
    }

    private fun createVm() {
        val listOfFragments = listOf(
                immigrationConnectFragment,
                studyConnectFragment
        )
        val viewPagerAdapter = ConnectViewPagerAdapter(childFragmentManager, listOfFragments as List<Fragment>, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        binding.pager.adapter = viewPagerAdapter
        binding.pager.offscreenPageLimit = 2
        binding.tabCategory.setupWithViewPager(binding.pager)
       binding.pager. addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if(position==0){
                    immigrationConnectFragment.onRefresh()
                }
                if(position==1){
                    studyConnectFragment.onRefresh()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

    }
}