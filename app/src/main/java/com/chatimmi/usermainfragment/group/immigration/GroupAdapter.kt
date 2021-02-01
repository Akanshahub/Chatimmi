package com.chatimmi.usermainfragment.group.immigration

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemGrouplistBinding


abstract class GroupAdapter(val layout: Int, groupViewModel: GroupViewModel) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: GroupViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemGrouplistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
            //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)
        return GroupAdapter.ViewHolder(binding)
    }

    override fun getItemCount()=10

    class ViewHolder(binding: SingleItemGrouplistBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: SingleItemGrouplistBinding

        init {
            this.binding = binding
        }

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       /* holder.binding.stack.setImageLists(setImageResources())
        holder.binding.stack.setOnImageClickListener {
    }
*/
        holder.binding.join.setOnClickListener {
            onitemBack()
        }
        holder.binding.lock.setOnClickListener {
            onLockCallBack()
        }

    }

    abstract fun onitemBack()
    abstract fun onLockCallBack()
    open fun setImageResources(): ArrayList<Any>? {
        val imageLists: ArrayList<Any> = ArrayList()
        imageLists.add("https://www.shutterstock.com/image-photo/micro-peacock-feather-hd-imagebest-texture-1127238599")
        imageLists.add("https://www.shutterstock.com/image-photo/micro-peacock-feather-hd-imagebest-texture-1127238599")
        imageLists.add("https://www.shutterstock.com/image-photo/micro-peacock-feather-hd-imagebest-texture-1127238599")
        imageLists.add("https://www.shutterstock.com/image-photo/micro-peacock-feather-hd-imagebest-texture-1127238599")
        return imageLists
    }


}

