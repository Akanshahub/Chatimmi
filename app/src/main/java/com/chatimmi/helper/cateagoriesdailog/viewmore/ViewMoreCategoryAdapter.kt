package com.chatimmi.helper.cateagoriesdailog.viewmore

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemViewMoreBinding
import com.chatimmi.databinding.SingleMarketPlaceBinding
import com.chatimmi.helper.cateagoriesdailog.CateagoriesConnectViewModel
import com.chatimmi.usermainfragment.marketplace.MarketPlaceAdapter
import com.chatimmi.usermainfragment.marketplace.MarketPlaceViewModel

class ViewMoreCategoryAdapter (val layout: Int, cateagoriesConnectViewModel: CateagoriesConnectViewModel) : RecyclerView.Adapter<ViewMoreCategoryAdapter.ViewHolder>() {
    var context: Activity? = null
    private var marketPlaceViewModel: MarketPlaceViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemViewMoreBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)

        return ViewMoreCategoryAdapter.ViewHolder(binding)
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }


    class ViewHolder(binding: SingleItemViewMoreBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemViewMoreBinding = binding

    }

}