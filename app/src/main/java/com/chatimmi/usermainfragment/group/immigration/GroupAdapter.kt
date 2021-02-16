package com.chatimmi.usermainfragment.group.immigration

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
import com.chatimmi.databinding.SingleItemGrouplistBinding
import com.chatimmi.usermainfragment.group.immigration.GroupListResponse.*
import java.util.*


abstract class GroupAdapter(val layout: Int, private var groupListItem: ArrayList<Data.Group>) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {
    lateinit var context: Context
    lateinit var session: Session
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        context = parent.context
        session = Session(context)

        val binding: SingleItemGrouplistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return GroupAdapter.GroupViewHolder(binding)

    }

    override fun getItemCount(): Int = groupListItem.size

    class GroupViewHolder(val binding: SingleItemGrouplistBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val groupListItem: Data.Group = groupListItem[position]

        Log.d("bnjnknk", "position --- : $position")
        holder.binding.tvName.text = groupListItem.groupName
        holder.binding.rl.text = groupListItem.categoryName
        //showOverlapImages(groupListItem.member_list,holder,groupListItem.member_list==3)

        holder.binding.lock.setOnClickListener {
            onLockCallBack()
        }
        holder.binding.rlCard.setOnClickListener() {
            onCardCallBack(groupListItem)
        }
        holder.binding.btJoin.setOnClickListener {
            onitemBack(groupListItem)
        }

        groupListItem.memberList.let {
            Log.d("fasbfjas", "onBindViewHolder: ${it.size}")
            if (groupListItem.memberList.isNotEmpty()) {
                showOverlapImages(groupListItem.memberList, holder)

            }
        }

        if (groupListItem.groupScope == 2) {
            holder.binding.lock.visibility = View.VISIBLE
            if (groupListItem.is_group_connect == 2) {
                holder.binding.tvRequestPending.visibility = View.VISIBLE
                holder.binding.ivChat.visibility = View.GONE
                holder.binding.btJoin.visibility = View.GONE
            } else if (groupListItem.is_group_connect == 1) {
                holder.binding.tvRequestPending.visibility = View.GONE
                holder.binding.ivChat.visibility = View.VISIBLE
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


    abstract fun onitemBack(groupID: Data.Group)
    abstract fun onCardCallBack(groupListItem: Data.Group)
    abstract fun onLockCallBack()
    fun addData(list: List<Data.Group>) {
        groupListItem.clear()
        groupListItem.addAll(list)
        notifyDataSetChanged()

    }

    fun filter(charText: String) {
        var tempGroupListItem: ArrayList<Data.Group> = ArrayList()
        tempGroupListItem.addAll(groupListItem)

        var charText = charText
        charText = charText.toLowerCase()
        groupListItem.clear()
        if (charText.length == 0) {
            Log.v("charText", "" + charText)
            tempGroupListItem.let { groupListItem.addAll(it) }
            searchingResult(false)
        } else {
            Log.v("charText1", "" + charText)
            for (i in 0 until tempGroupListItem.size) {
                //  if (listType.equals("contactType")) {
                if (tempGroupListItem.get(i).groupName?.toLowerCase()
                        !!.contains(charText)
                ) {
                    groupListItem.add(tempGroupListItem.get(i))
                    Log.v("mSearchHistory", "" + groupListItem)
                }
                // }
            }

            if (groupListItem.size == 0) {
                searchingResult(true)
            } else {
                searchingResult(false)

            }

        }
        notifyDataSetChanged()


    }

    //  interface Searching{
    abstract fun searchingResult(isFound: Boolean)

    @SuppressLint("SetTextI18n")
    private fun showOverlapImages(list: List<Data.Group.Member?>?, holder: GroupViewHolder) {
        for (i in list!!.indices) {
            Log.d("fnaslfnlas", "showOverlapImages: $i")
            val getList = list[i]
            if (i == 0) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.GONE
                holder.binding.ivThree.visibility = View.GONE
                holder.binding.ivShowCount.visibility = View.GONE
                holder.binding.rll.visibility = View.VISIBLE
                Log.d("mnknknnknk", "showOverlapImages: ${getList?.avatar}")
                Glide.with(holder.binding.ivOne.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivOne)
            } else if (i == 1) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.GONE
                holder.binding.ivShowCount.visibility = View.GONE
                holder.binding.rll.visibility = View.VISIBLE
                Glide.with(holder.binding.ivTwo.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivTwo)
            } else if (i == 2) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.VISIBLE
                holder.binding.rll.visibility = View.VISIBLE
                holder.binding.ivShowCount.visibility = View.GONE
                Glide.with(holder.binding.ivThree.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivThree)
            } else if (i > 3) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.VISIBLE
                holder.binding.tvImgCount.visibility = View.VISIBLE
                holder.binding.ivShowCount.visibility = View.VISIBLE

                holder.binding.rll.visibility = View.VISIBLE
                Glide.with(holder.binding.ivOne.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivOne)
                Glide.with(holder.binding.ivTwo.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivTwo)
                Glide.with(holder.binding.ivThree.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivThree)

                holder.binding.tvImgCount.text = "" + "+${list.size - 3}"
            }
        }
    }


}




