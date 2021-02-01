package com.chatimmi.usermainfragment.connectfragment.study

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemStudyConnectBinding


abstract class StudyConnectAdapter  (val layout: Int, studyConnectViewModel: StudyConnectViewModel) : RecyclerView.Adapter<StudyConnectAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: StudyConnectViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemStudyConnectBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return StudyConnectAdapter.ViewHolder(binding)
        //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)
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

    class ViewHolder(binding: SingleItemStudyConnectBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: SingleItemStudyConnectBinding
        init {
            this.binding = binding
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.connect.setOnClickListener {
            onConnectCall()
        }
    }
    abstract fun onConnectCall()
}