package com.chatimmi.usermainfragment.connectfragment.details

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemConnectDetailsBinding

class ConnectDetailsAdapter (val layout: Int, connectDetailsViewModel: ConnectDetailsViewModel) : RecyclerView.Adapter<ConnectDetailsAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: ConnectDetailsViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemConnectDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ConnectDetailsAdapter.ViewHolder(binding)
    }

    override fun getItemCount()=2

    class ViewHolder(binding: SingleItemConnectDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemConnectDetailsBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}
