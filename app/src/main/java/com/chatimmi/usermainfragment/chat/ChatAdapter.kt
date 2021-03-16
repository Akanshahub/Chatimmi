package com.chatimmi.usermainfragment.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chatimmi.base.BaseActivitykt
import com.chatimmi.databinding.SingleChatItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


abstract class ChatAdapter(val layout: Int, private var listItem: ArrayList<ChatHistoryResponse.Data.History>, var context: BaseActivitykt) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var viewModel: ChatViewModel? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context as BaseActivitykt
        val binding: SingleChatItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return ChatAdapter.ViewHolder(binding)
        //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)

    }

    override fun getItemCount() = listItem.size

    class ViewHolder(binding: SingleChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleChatItemBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.rl.setOnClickListener() {
            onLockCallBack(listItem[position])
        }
        if (position == itemCount - 1) {
            holder.binding.views.visibility = View.VISIBLE
        } else {
            holder.binding.views.visibility = View.GONE
        }
        val groupListItem: ChatHistoryResponse.Data.History = listItem[position]
        Glide.with(holder.binding.image.context).load(groupListItem.avatar).into(holder.binding.image)
        holder.binding.tvName.text = groupListItem.fullName

/*        val sd = SimpleDateFormat("hh:mm a")
        try {
            // String date = sd.format(new Date((Long) chat.timestamp));
            holder.binding.tvTime.text = formatDateFromDateString(ChattingAdapter.DATE_FORMAT_12, ChattingAdapter.DATE_FORMAT_13, groupListItem.msgTime)
        } catch (e: Exception) {
            Log.e("Exception", e.message!!)
        }*/
        context.getYesterdayDate(groupListItem.msgTime!!, holder.binding.tvTime)

        if (groupListItem.unreadCountForUser !== 0) {
            holder.binding.tvCount.visibility = View.VISIBLE
            holder.binding.tvCount.text = groupListItem.unreadCountForUser.toString()
        } else {
            holder.binding.tvCount.visibility = View.GONE
        }

        if (groupListItem.isOnline == 1) {
            holder.binding.llIsOnlineUser.visibility = View.VISIBLE

        } else {
            holder.binding.llIsOnlineUser.visibility = View.GONE
        }

        if (groupListItem.typing == 1) {
            holder.binding.tvLastMsg.text = "typing..."
        } else {
            if (groupListItem.isImage == 1) {
                holder.binding.tvLastMsg.text = "Image"

            } else {
                holder.binding.tvLastMsg.text = groupListItem.lastMessage
            }

        }

        if (groupListItem.msgforuser == "1") {
            holder.binding.tvLastMsg.visibility = View.VISIBLE
        } else {
            holder.binding.tvLastMsg.visibility = View.GONE
        }

    }

    @Throws(ParseException::class)
    fun formatDateFromDateString(
            inputDateFormat: String?, outputDateFormat: String?,
            inputDate: String?,
    ): String? {
        val formatter = SimpleDateFormat(inputDateFormat, Locale.ENGLISH)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val value = formatter.parse(inputDate)
        val sd = SimpleDateFormat(outputDateFormat, Locale.getDefault())
        sd.timeZone = TimeZone.getDefault()
        return sd.format(value)
    }


    fun addData(list: List<ChatHistoryResponse.Data.History>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()

    }

    abstract fun onLockCallBack(consultant: ChatHistoryResponse.Data.History)
}