package com.chatimmi.usermainfragment.group.study

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemStudyBinding

abstract class StudyAdapter (val layout: Int, groupViewModel: StudyViewModel) : RecyclerView.Adapter<StudyAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: StudyViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemStudyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return StudyAdapter.ViewHolder(binding)
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

    class ViewHolder(binding: SingleItemStudyBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: SingleItemStudyBinding
        init {
            this.binding = binding
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       /* holder.binding.stack.setImageLists(setImageResources())

        holder.binding.stack.setOnImageClickListener {
        }*/

        holder.binding.join.setOnClickListener {
            onitemBack()
        }
        holder.binding.lock.setOnClickListener {
            onLockCallBack()
        }
    }

    abstract fun onitemBack()

    abstract fun onLockCallBack()
}