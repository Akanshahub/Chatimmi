package com.chatimmi.usermainfragment.group.immigration.details

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chatimmi.databinding.SingleItemJoinMemberBinding


class ImmigrationDetailsAdapter (val layout: Int, groupViewModel: ImmigrationDetailsViewModel) : RecyclerView.Adapter<ImmigrationDetailsAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: ImmigrationDetailsViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemJoinMemberBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ImmigrationDetailsAdapter.ViewHolder(binding)

    }

    override fun getItemCount()=10

    class ViewHolder(binding: SingleItemJoinMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemJoinMemberBinding = binding
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}
