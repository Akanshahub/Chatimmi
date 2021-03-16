package com.chatimmi.usermainfragment.connectfragment.study

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chatimmi.databinding.SingleItemStudyConnectBinding
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ConsultantListResponce


abstract class StudyConnectAdapter(val layout: Int, studyConnectViewModel: StudyConnectViewModel, private var list: java.util.ArrayList<ConsultantListResponce.Data.Consultant>) : RecyclerView.Adapter<StudyConnectAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: StudyConnectViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemStudyConnectBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return StudyConnectAdapter.ViewHolder(binding)
    }


    override fun getItemCount() = list.size


    class ViewHolder(binding: SingleItemStudyConnectBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        var binding: SingleItemStudyConnectBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == list.size - 1) {
            holder.binding.views.visibility = View.VISIBLE
        } else {
            holder.binding.views.visibility = View.GONE
        }
        Glide.with(holder.binding.ivImage.context).load(list[position].avatar).into(holder.binding.ivImage)
        holder.binding.carnegie.text = list[position].fullName
        holder.binding.uk.text = list[position].categoryName

        holder.itemView.setOnClickListener {
            onCardCallBack(list[position])
        }
        holder.binding.connect.setOnClickListener {
            onConnectCallBack(list[position].userID)
        }
        holder.binding.ivChat.setOnClickListener {

        }

        if (list[position].isConnect == 0) {
            holder.binding.connect.visibility = View.VISIBLE
            holder.binding.ivChat.visibility = View.GONE
        } else if (list[position].isConnect == 1) {
            holder.binding.connect.visibility = View.GONE
            holder.binding.ivChat.visibility = View.VISIBLE
        }

    }

    fun addData(consultantList: List<ConsultantListResponce.Data.Consultant>) {
        this.list.clear()
        this.list.addAll(consultantList)
        notifyDataSetChanged()
    }

    abstract fun onConnectCall()
    abstract fun onConnectCallBack(userID: Int)
    abstract fun onCardCallBack(item: ConsultantListResponce.Data.Consultant)
}