package com.chatimmi.usermainfragment.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentChatBinding
import com.chatimmi.usermainfragment.group.study.StudyFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : BaseFragment() {
    private var viewModel: ChatViewModel? = null
    lateinit var chatRepository: ChatRepository
    lateinit var binding: FragmentChatBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        chatRepository = ChatRepository(activity)
        val factory =ChatViewModelFactory(chatRepository)
        viewModel = ViewModelProviders.of(this, factory)[ChatViewModel::class.java]
        viewModel?.init()

        binding.chatModel = viewModel
        setupBindings()

        /*  viewModel?.getAdapterClickObserver()?.observeForever {
              it?.let {
                  if(it){
                      JoinBottomDialog.newInstance().show(childFragmentManager)
                  }
              }
          }*/

        return binding.root
    }

    private fun setupBindings() {
       chatRepository.getResponseData().observe(activity, Observer {
           it?.let {
               when (it) {
                   is UIStateManager.Success<*> -> {
                       val getData = it.data as ChatHistoryResponse
                       Log.d("bnjnknk", "setupBindings: ${getData.data!!.historyList.size}")

                      // group.addAll(getData.data!!.historyList)

                       if (getData.data!!.historyList.isEmpty()) {
                           binding.rvMain.visibility = View.GONE
                         /*  binding.noDataAvailable.visibility = View.VISIBLE
                           viewModel?.clearList()*/
                       } else {
                           viewModel?.getAdapter()?.let {
                               binding.rvMain.visibility = View.VISIBLE
                             //  binding.noDataAvailable.visibility = View.GONE
                               binding.rvMain.adapter = viewModel?.getAdapter()
                               viewModel?.getAdapter()!!.addData(getData.data!!.historyList)
                               viewModel?.getAdapter()!!.notifyDataSetChanged()
                           }
                       }


                   }
                   is UIStateManager.Error -> {
                      // activity.showMsg(it.msg)
                   }
                   is UIStateManager.Loading -> {
                       if (it.shouldShowLoading) {
                           activity.showLoader()
                       } else {
                           activity.hideLoader()
                       }

                   }
                   is UIStateManager.ErrorCode -> {
                       activity.callLogoutApi()

                   }
                   else -> {

                   }
               }
           }


       })


    }
/*    private fun getIstypingUser() {
        mSocket!!.on("typing") { args ->
            Log.e("typing", args[0].toString())
            val data = args[0] as JSONObject
            try {

               activity.runOnUiThread {
                    val userID = data.getString("userID")

                    if (userID == userId.toString()) {
                        val typingStatus = data.getInt("is_typing")
                        if (typingStatus == 1) {

                        } else {

                        }

                    }
                }
            } catch (e: Exception) {

            }
        }
    }*/
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                StudyFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}