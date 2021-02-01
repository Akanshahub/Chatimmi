package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemMyConsultantBinding
import com.chatimmi.databinding.SingleItemMyStudyBinding

class MyConsultantsStudyAdapter(val layout: Int, myConsultantsImmigrationViewModel: MyConsultantsImmigrationViewModel) : RecyclerView.Adapter<MyConsultantsStudyAdapter.ViewHolder>() {
    var context: Activity? = null

    private var viewModel: MyConsultantsImmigrationViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemMyStudyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return MyConsultantsStudyAdapter.ViewHolder(binding)
        //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)
    }

    override fun getItemCount()=10

    class ViewHolder(binding: SingleItemMyStudyBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemMyStudyBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
    //  abstract fun onConnectCall()
}