package com.chatimmi.usermainfragment.group.immigration.details

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chatimmi.R
import com.chatimmi.databinding.SingleItemJoinMemberBinding
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse
import com.squareup.picasso.Picasso
import java.util.ArrayList


abstract class ImmigrationDetailsAdapter(val layout: Int, private var groupMemberList: ArrayList<ImmigrationDetailsResponse.Data.GroupMember>) : RecyclerView.Adapter<ImmigrationDetailsAdapter.ViewHolder>() {

    lateinit var context: Context
    private var viewModel: ImmigrationDetailsViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding: SingleItemJoinMemberBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ImmigrationDetailsAdapter.ViewHolder(binding)

    }

    override fun getItemCount(): Int = groupMemberList.size


    class ViewHolder(binding: SingleItemJoinMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemJoinMemberBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupListItem: ImmigrationDetailsResponse.Data.GroupMember = groupMemberList[position]
        holder.binding.tvName.text = groupListItem.fullName
        Glide.with(context).load(groupListItem.avatar).into(holder.binding.ivImages)

        holder.binding.connect.setOnClickListener() {
            onConnectCallBack(groupListItem.userID!!)
        }

        if (groupListItem.userType == 1) {
            holder.binding.tvUserType.visibility = View.GONE
            holder.binding.connect.visibility = View.GONE
            holder.binding.ivChat.visibility = View.GONE
        } else {
            holder.binding.tvUserType.visibility = View.VISIBLE
            holder.binding.tvUserType.text = context.getString(R.string.consultant)
            if (groupListItem.isConnect == 1) {
                holder.binding.ivChat.visibility = View.VISIBLE
                holder.binding.connect.visibility = View.GONE
            } else {
                holder.binding.ivChat.visibility = View.GONE
                holder.binding.connect.visibility = View.VISIBLE
            }
        }
    }

    fun addData(list: List<ImmigrationDetailsResponse.Data.GroupMember>) {
        this.groupMemberList.clear()
        this.groupMemberList.addAll(list)
        notifyDataSetChanged()

    }
    abstract fun onConnectCallBack(userID: Int)

}
