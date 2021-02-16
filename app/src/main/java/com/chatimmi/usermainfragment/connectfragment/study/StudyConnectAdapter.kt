package com.chatimmi.usermainfragment.connectfragment.study

import android.R
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chatimmi.databinding.SingleItemStudyConnectBinding
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ConsultantListResponce
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ImmigrationConnectAdapter


abstract class StudyConnectAdapter  (val layout: Int, studyConnectViewModel: StudyConnectViewModel, private var list: java.util.ArrayList<ConsultantListResponce.Data.Consultant>) : RecyclerView.Adapter<StudyConnectAdapter.ViewHolder>() {
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
    override fun getItemCount() = list.size


    class ViewHolder(binding: SingleItemStudyConnectBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        var binding: SingleItemStudyConnectBinding
        init {
            this.binding = binding
        }
    }
  /*  override fun onBindViewHolder(holder: ImmigrationConnectAdapter.ViewHolder, position: Int) {
        Glide.with(holder.binding.image.context).load(list[position].avatar).into(holder.binding.image)
        holder.binding.trnsport.text = list[position].fullName
        holder.binding.price.text = list[position].categoryName

        holder.itemView.setOnClickListener {
           // onCardCallBack(list[position])
        }
        holder.binding.connect.setOnClickListener {
           // onConnectCallBack(list[position].userID)
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
    }*/
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    
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
       /* holder.binding.connect.setOnClickListener {
            onConnectCall()
        }*/
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