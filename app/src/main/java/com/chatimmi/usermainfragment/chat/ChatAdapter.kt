package com.chatimmi.usermainfragment.chat

import android.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleChatItemBinding

abstract class ChatAdapter (val layout: Int, chtaViewModel: ChatViewModel) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: ChatViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleChatItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ChatAdapter.ViewHolder(binding)
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

    class ViewHolder(binding: SingleChatItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: SingleChatItemBinding
        init {
            this.binding = binding
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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