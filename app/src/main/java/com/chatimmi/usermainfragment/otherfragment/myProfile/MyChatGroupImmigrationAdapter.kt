package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemImmigrationlistBinding
import com.chatimmi.usermainfragment.connectfragment.study.StudyConnectViewModel

abstract class MyChatGroupImmigrationAdapter (val layout: Int, myChatGroupImmigrationViewModel: MyChatGroupImmigrationViewModel) : RecyclerView.Adapter<MyChatGroupImmigrationAdapter.ViewHolder>() {
     var context: Activity? = null
     private var viewModel: MyChatGroupImmigrationViewModel? = null
     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val binding: SingleItemImmigrationlistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
         return MyChatGroupImmigrationAdapter.ViewHolder(binding)
         //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)
     }

     override fun getItemCount()=10

     class ViewHolder(binding: SingleItemImmigrationlistBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
         var binding: SingleItemImmigrationlistBinding = binding
     }
     override fun onBindViewHolder(holder: ViewHolder, position: Int) {

     }
   //  abstract fun onConnectCall()
 }