package com.chatimmi.usermainfragment.marketplace

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleMarketPlaceBinding

abstract class MarketPlaceAdapter(val layout: Int, marketPlaceViewModel: MarketPlaceViewModel) : RecyclerView.Adapter<MarketPlaceAdapter.ViewHolder>() {
    var context: Activity? = null
    private var marketPlaceViewModel: MarketPlaceViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleMarketPlaceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)

        return MarketPlaceAdapter.ViewHolder(binding)
    }

    override fun getItemCount() = 10

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.card.setOnClickListener {
            onConnectCallBack()
        }
    }


    class ViewHolder(binding: SingleMarketPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        lateinit var binding: SingleMarketPlaceBinding

        init {
            this.binding = binding
        }
    }
    abstract fun onConnectCallBack()

}