package com.chatimmi.usermainfragment.marketplace.detials.school

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemSchoolBinding
import com.chatimmi.databinding.SingleItemStudyBinding
import com.chatimmi.usermainfragment.group.study.StudyAdapter
import com.chatimmi.usermainfragment.group.study.StudyViewModel

class SchoolAdapter (val layout: Int, schoolViewModel: SchoolViewModel) : RecyclerView.Adapter<SchoolAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: StudyViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemSchoolBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return SchoolAdapter.ViewHolder(binding)


    }

    override fun getItemCount()=10

    class ViewHolder(binding: SingleItemSchoolBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemSchoolBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }


}