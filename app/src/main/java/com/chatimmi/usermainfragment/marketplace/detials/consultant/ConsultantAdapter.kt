package com.chatimmi.usermainfragment.marketplace.detials.consultant

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemConsultantBinding

class ConsultantAdapter(val layout: Int, consultantViewModel: ConsultantViewModel) : RecyclerView.Adapter<ConsultantAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: ConsultantViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemConsultantBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ConsultantAdapter.ViewHolder(binding)


    }

    override fun getItemCount()=10

    class ViewHolder(binding: SingleItemConsultantBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemConsultantBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


}