package com.chatimmi.usermainfragment.group.study

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chatimmi.app.pref.Session
import com.chatimmi.databinding.SingleItemStudyBinding
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse
import com.squareup.picasso.Picasso

abstract class StudyAdapter(val layout: Int, private var groupListItem: ArrayList<GroupListResponse.Data.Group>) : RecyclerView.Adapter<StudyAdapter.StudyViewHolder>() {
    lateinit var context: Context
    lateinit var session: Session

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyViewHolder {
        context = parent.context
        session = Session(context)

        val binding: SingleItemStudyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return StudyAdapter.StudyViewHolder(binding)


    }

    override fun getItemCount(): Int = groupListItem.size

    class StudyViewHolder(binding: SingleItemStudyBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemStudyBinding = binding
    }

    override fun onBindViewHolder(holder: StudyViewHolder, position: Int) {
        val groupListItem: GroupListResponse.Data.Group = groupListItem[position]
        Log.d("bnjnknk", "position --- : ${position}")
        holder.binding.tvName.text = groupListItem.groupName
        holder.binding.rl.text = groupListItem.categoryName

        holder.binding.btJoin.setOnClickListener {
            val groupID = groupListItem
            onitemBack(groupListItem)
        }
        holder.binding.rlCard.setOnClickListener() {
            onCardCallBack(groupListItem)
        }
        groupListItem.memberList.let {
            Log.d("fasbfjas", "onBindViewHolder: ${it.size}")
            if (groupListItem.memberList.isNotEmpty()) {
                showOverlapImages(groupListItem.memberList, holder)
            }
        }
        /*if (groupListItem.groupScope == 2) {
            holder.binding.lock.visibility = View.VISIBLE
            for (j in groupListItem.memberList.indices) {
                if (groupListItem.memberList[j].userID == session.getUserData()?.data?.user_details?.userID) {
                    if (groupListItem.memberList[j].groupStatus == 2) {

                        holder.binding.tvRequestPending.visibility = View.VISIBLE
                        holder.binding.ivChat.visibility = View.GONE
                        holder.binding.btJoin.visibility = View.GONE

                    } else {
                        holder.binding.tvRequestPending.visibility = View.GONE
                        holder.binding.ivChat.visibility = View.VISIBLE
                        holder.binding.btJoin.visibility = View.GONE

                    }
                } else {
                    holder.binding.ivChat.visibility=View.GONE
                    holder.binding.tvRequestPending.visibility=View.GONE
                    holder.binding.btJoin.visibility=View.VISIBLE
                }
            }


        } else {
            holder.binding.lock.visibility = View.GONE
            for (j in groupListItem.memberList.indices) {
                if (groupListItem.memberList[j].userID == session.getUserData()?.data?.user_details?.userID) {
                    holder.binding.ivChat.visibility = View.VISIBLE
                    holder.binding.btJoin.visibility = View.GONE

                } else {
                    holder.binding.ivChat.visibility = View.GONE
                    holder.binding.btJoin.visibility=View.VISIBLE

                }
            }*/


        if (groupListItem.groupScope == 2) {
            holder.binding.lock.visibility = View.VISIBLE
            if (groupListItem.is_group_connect == 2) {
                holder.binding.tvRequestPending.visibility = View.VISIBLE
                holder.binding.ivChat.visibility = View.GONE
                holder.binding.btJoin.visibility = View.GONE
            } else if (groupListItem.is_group_connect == 1) {
                holder.binding.tvRequestPending.visibility = View.VISIBLE
                holder.binding.ivChat.visibility = View.GONE
                holder.binding.btJoin.visibility = View.GONE
            } else {
                holder.binding.tvRequestPending.visibility = View.GONE
                holder.binding.ivChat.visibility = View.GONE
                holder.binding.btJoin.visibility = View.VISIBLE
            }


        } else {
            holder.binding.lock.visibility = View.GONE
            if (groupListItem.is_group_connect == 1) {
                holder.binding.tvRequestPending.visibility = View.GONE
                holder.binding.ivChat.visibility = View.VISIBLE
                holder.binding.btJoin.visibility = View.GONE
            } else {
                holder.binding.tvRequestPending.visibility = View.GONE
                holder.binding.ivChat.visibility = View.GONE
                holder.binding.btJoin.visibility = View.VISIBLE
            }
        }
    }

    abstract fun onitemBack(groupID: GroupListResponse.Data.Group)

    abstract fun onLockCallBack()
    abstract fun onCardCallBack(groupListItem: GroupListResponse.Data.Group)

    fun addData(list: List<GroupListResponse.Data.Group>) {
        groupListItem.clear()
        groupListItem.addAll(list)
        notifyDataSetChanged()

    }
    @SuppressLint("SetTextI18n")
    private fun showOverlapImages(list: List<GroupListResponse.Data.Group.Member?>?, holder: StudyViewHolder) {
        for (i in list!!.indices) {
            Log.d("fnaslfnlas", "showOverlapImages: ${i}")
            val getList = list[i]
            if (i == 0) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.GONE
                holder.binding.ivThree.visibility = View.GONE
                holder.binding.rll.visibility = View.VISIBLE
                Glide.with(context).load(getList!!.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivOne)

            } else if (i == 1) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.GONE
                holder.binding.rll.visibility = View.VISIBLE
                Glide.with(context).load(getList!!.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivTwo)

            } else if (i == 2) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.VISIBLE
                holder.binding.rll.visibility = View.VISIBLE
                Glide.with(context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivThree)
            } else if (i > 3) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.VISIBLE
                holder.binding.tvImgCount.visibility = View.VISIBLE
                holder.binding.ivShowCount.visibility = View.VISIBLE
               // holder.binding.count.visibility = View.VISIBLE
                holder.binding.rll.visibility = View.VISIBLE
                /* Picasso.get().load(getList?.avatar).into(holder.binding.ivOne)
                 Picasso.get().load(getList?.avatar).into(holder.binding.ivTwo)
                 Picasso.get().load(getList?.avatar).into(holder.binding.ivThree)*/
                Glide.with(context).load(getList?.avatar).into(holder.binding.ivOne)
                Glide.with(context).load(getList?.avatar).into(holder.binding.ivTwo)
                Glide.with(context).load(getList?.avatar).into(holder.binding.ivThree)
                holder.binding.tvImgCount.text = ""+"+${list.size - 3}"
            }
        }
    }
}


