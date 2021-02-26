package com.chatimmi.usermainfragment.otherfragment.myProfile

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chatimmi.R
import com.chatimmi.databinding.SingleItemImmigrationlistBinding
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


abstract class MyChatGroupImmigrationAdapter(val layout: Int, private var listItem: ArrayList<GetProfileChatGroupResponse.Data.MyChatGroupList>) : RecyclerView.Adapter<MyChatGroupImmigrationAdapter.ViewHolder>() {
    var context: Activity? = null
    private var viewModel: MyChatGroupImmigrationViewModel? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: SingleItemImmigrationlistBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), layout, parent, false)
        return MyChatGroupImmigrationAdapter.ViewHolder(binding)
        //   val view = LayoutInflater.from(parent.context).inflate(layout,parent, false)
    }

    override fun getItemCount(): Int = listItem.size

    class ViewHolder(binding: SingleItemImmigrationlistBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: SingleItemImmigrationlistBinding = binding
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val groupListItem: GetProfileChatGroupResponse.Data.MyChatGroupList = listItem[position]
        holder.binding.tvName.text = groupListItem.groupName
        holder.binding.rl.text = groupListItem.subCategoryName

        if (groupListItem.groupScope == 2) {
            holder.binding.lock.visibility = View.VISIBLE
        }else{
            holder.binding.lock.visibility = View.GONE
        }
        groupListItem.memberList.let {
            Log.d("fasbfjas", "onBindViewHolder: ${it!!.size}")
            if (groupListItem.memberList!!.isNotEmpty()) {
                showOverlapImages(groupListItem.memberList, holder)

            }
        }

    }

    fun addData(list: List<GetProfileChatGroupResponse.Data.MyChatGroupList>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()

    }

    private fun showOverlapImages(list: List<GetProfileChatGroupResponse.Data.MyChatGroupList.Member?>?, holder: MyChatGroupImmigrationAdapter.ViewHolder) {
        for (i in list!!.indices) {
            val getList = list[i]
            Log.d("fnaslfnlas", "showOverlapImages: ${getList?.avatar}")
            if (i == 0) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.GONE
                holder.binding.ivThree.visibility = View.GONE
                // holder.binding.ivShowCount.visibility = View.GONE
                holder.binding.rll.visibility = View.VISIBLE
                Log.d("mnknknnknk", "showOverlapImages: ${getList?.avatar}")
                Glide.with(holder.binding.ivOne.context).load(getList?.avatar).error(R.drawable.user_placeholder_img).placeholder(R.drawable.user_placeholder_img).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivOne)

            } else if (i == 1) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.GONE
                //   holder.binding.ivShowCount.visibility = View.GONE
                holder.binding.rll.visibility = View.VISIBLE
                Glide.with(holder.binding.ivOne.context).load(getList?.avatar).error(R.drawable.user_placeholder_img).placeholder(R.drawable.user_placeholder_img).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivTwo)
            } else if (i == 2) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.VISIBLE
                holder.binding.rll.visibility = View.VISIBLE
                //  holder.binding.ivShowCount.visibility = View.GONE
                Glide.with(holder.binding.ivOne.context).load(getList?.avatar).error(R.drawable.user_placeholder_img).placeholder(R.drawable.user_placeholder_img).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivThree)
            } else if (i >= 3) {
                holder.binding.people.visibility = View.VISIBLE
                holder.binding.ivOne.visibility = View.VISIBLE
                holder.binding.ivTwo.visibility = View.VISIBLE
                holder.binding.ivThree.visibility = View.VISIBLE
                holder.binding.tvImgCount.visibility = View.VISIBLE
                holder.binding.ivShowCount.visibility = View.VISIBLE
                holder.binding.rll.visibility = View.VISIBLE
                /*Glide.with(holder.binding.ivOne.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivOne)
                Glide.with(holder.binding.ivTwo.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivTwo)
                Glide.with(holder.binding.ivThree.context).load(getList?.avatar).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.binding.ivThree)*/
                holder.binding.tvImgCount.text = "" + "+${list.size - 3}"
            }
        }
    }


    open fun getBitmapFromURL(src: String?): Bitmap? {
        var bitmap: Bitmap? = null
        Thread(Runnable {
            try {
                val url = URL(src)
                val connection: HttpURLConnection = url
                        .openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(input)

            } catch (e: Exception) {
            }
        }).start()

        return bitmap
    }

    //  abstract fun onConnectCall()
}