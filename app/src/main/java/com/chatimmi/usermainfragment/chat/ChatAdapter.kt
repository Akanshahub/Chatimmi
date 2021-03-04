package com.chatimmi.usermainfragment.chat

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.chatimmi.databinding.SingleChatItemBinding
import java.util.*


abstract class ChatAdapter (val layout: Int, chatViewModel: ChatViewModel) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: ChatViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleChatItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ChatAdapter.ViewHolder(binding)
        //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)

    }
    private fun setImageResources(): ArrayList<Any> {
        val imageLists: ArrayList<Any> = ArrayList()
        imageLists.add(R.color.black)
        imageLists.add(R.color.darker_gray)
        imageLists.add(R.color.holo_green_dark)
        imageLists.add(R.color.holo_blue_bright)
        return imageLists
    }
    override fun getItemCount()=10

    class ViewHolder(binding: SingleChatItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: SingleChatItemBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==itemCount-1){
            holder.binding.views.visibility=View.VISIBLE
        }else{
            holder.binding.views.visibility=View.GONE
        }


/*        holder.binding.rl.setOnClickListener(){
            val intent = Intent(context, ChatActivity::class.java)
           *//* intent.putExtra("groupName", it.fullName)
            intent.putExtra("categoryName", it.categoryName)
            intent.putExtra("subCategoryName", it.subCategoryName)
            intent.putExtra("userId", it.userID)
            intent.putExtra("avatar", it.avatar)
            intent.putExtra("emailId", it.email)*//*
            //intent.putExtra("position", group.indexOf(it))
           context!!. startActivity(intent)
        }*/
      /*  holder.binding.stack.setImageLists(setImageResources())

        holder.binding.stack.setOnImageClickListener {
        }

        holder.binding.rlLayout.setOnClickListener {
            val intent = Intent(holder.itemView.context, ImmigrationDetailsActivity::class.java)
            //listener?.onClick(AlbumsData)
            intent.putExtra("dd", "ff")
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.lock.setOnClickListener {
            onLockCallBack()
        }*/
    }

    abstract fun onLockCallBack()
}