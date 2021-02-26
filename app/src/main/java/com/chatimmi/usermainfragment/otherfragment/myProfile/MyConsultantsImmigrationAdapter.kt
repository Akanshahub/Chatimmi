package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chatimmi.databinding.SingleItemMyConsultantBinding
import java.util.*


class MyConsultantsImmigrationAdapter (val layout: Int, private var listItem: ArrayList<GetProfileMyConsultantsResonse.Data.MyConsultantsList>) : RecyclerView.Adapter<MyConsultantsImmigrationAdapter.ViewHolder>() {


    private var viewModel: MyConsultantsImmigrationViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemMyConsultantBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return MyConsultantsImmigrationAdapter.ViewHolder(binding)
        //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)
    }

    override fun getItemCount(): Int = listItem.size

    class ViewHolder(binding: SingleItemMyConsultantBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemMyConsultantBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupListItem: GetProfileMyConsultantsResonse.Data.MyConsultantsList = listItem[position]
        holder.binding.tvName.text=groupListItem.fullName
        holder.binding.tvCategory.text=groupListItem.categoryname
        Glide.with(holder.binding.image.context).load(groupListItem.avatar).into(holder.binding.image)
    }
    fun addData(list: List<GetProfileMyConsultantsResonse.Data.MyConsultantsList>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()

    }
    //  abstract fun onConnectCall()
}