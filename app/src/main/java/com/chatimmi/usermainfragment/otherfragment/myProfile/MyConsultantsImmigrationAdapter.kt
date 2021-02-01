package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemMyConsultantBinding


class MyConsultantsImmigrationAdapter (val layout: Int, myConsultantsImmigrationViewModel: MyConsultantsImmigrationViewModel) : RecyclerView.Adapter<MyConsultantsImmigrationAdapter.ViewHolder>() {
    var context: Activity? = null

    private var viewModel: MyConsultantsImmigrationViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemMyConsultantBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return MyConsultantsImmigrationAdapter.ViewHolder(binding)
        //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)
    }

    override fun getItemCount()=10

    class ViewHolder(binding: SingleItemMyConsultantBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemMyConsultantBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
    //  abstract fun onConnectCall()
}