package com.chatimmi.usermainfragment.connectfragment.immigrationconnect

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemConnectImmigrationBinding

abstract class ImmigrationConnectAdapter  (val layout: Int, immigrationConnectViewmodel: ImmigrationConnectViewModel) : RecyclerView.Adapter<ImmigrationConnectAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: ImmigrationConnectViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemConnectImmigrationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ImmigrationConnectAdapter.ViewHolder(binding)
    }
    private fun setImageResources(): ArrayList<Any>? {
        val imageLists: ArrayList<Any> = ArrayList()
        imageLists.add(R.color.black)
        imageLists.add(R.color.darker_gray)
        imageLists.add(R.color.holo_green_dark)
        imageLists.add(R.color.holo_blue_bright)
        return imageLists
    }
    override fun getItemCount()=10

    class ViewHolder(binding: SingleItemConnectImmigrationBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: SingleItemConnectImmigrationBinding
        init {
            this.binding = binding
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.connect.setOnClickListener {
            onConnectCallBack()
        }


    }

    abstract fun onConnectCallBack()
}