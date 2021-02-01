package com.chatimmi.usermainfragment.marketplace

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentMarketPlaceBinding
import com.chatimmi.usermainfragment.activity.notification.NotificationActivity
import com.chatimmi.usermainfragment.marketplace.filtermarket.MarketFilterActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarketPlaceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarketPlaceFragment : BaseFragment(),CommonTaskPerformer {
    private var marketPlaceViewModel:MarketPlaceViewModel?=null
    lateinit var binding: FragmentMarketPlaceBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_market_place,container,false)
        binding.notification.setOnClickListener {
            val intent = Intent(getActivity(), NotificationActivity::class.java)
            //listener?.onClick(AlbumsData)
            intent.putExtra("dd", "ff")
            getActivity()?.startActivity(intent)
        }
        binding.filter.setOnClickListener {
            val intent = Intent(getActivity(), MarketFilterActivity::class.java)
            //listener?.onClick(AlbumsData)
            intent.putExtra("dd", "ff")
            getActivity()?.startActivity(intent)
        }
   setUpBinding(savedInstanceState)
        return binding.root
    }
    fun setUpBinding(savedInstanceState: Bundle?) {
        marketPlaceViewModel = ViewModelProviders.of(this)[MarketPlaceViewModel::class.java]
        if (savedInstanceState == null) {
            marketPlaceViewModel?.init(this)
        }
        binding.marketplace = marketPlaceViewModel
    }
    companion object{
        @JvmStatic
        fun newInstance(param1: String) =
                MarketPlaceFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun <T> performAction(clazz: Class<T>) {
        Intent(requireContext(),clazz).apply {
            startActivity(this)}
    }

    override fun showMsg(msg: String) {
        requireContext().showToast(msg)
    }

    override fun dismissDialog() {

    }

}