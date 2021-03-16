package com.chatimmi.usermainfragment.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.Chatimmi
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentChatBinding
import com.chatimmi.usermainfragment.connectfragment.chat.ChatActivity
import com.chatimmi.usermainfragment.group.study.StudyFragment
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

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
    lateinit var group: ArrayList<ChatHistoryResponse.Data.History>
    var groupName = ""
    private var userId = 0
    var categoryName = ""
    var subCategoryName = ""
    var avatar: String = ""
    lateinit var session: Session
    var emailId: String = ""
    var myUserId = ""
    var IsOnline = ""
    lateinit var mSocket: Socket


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        chatRepository = ChatRepository(activity)
        val factory = ChatViewModelFactory(chatRepository)
        viewModel = ViewModelProviders.of(this, factory)[ChatViewModel::class.java]
        viewModel?.init()
        session = Session(activity)
        binding.chatModel = viewModel
        group = ArrayList()
        mSocket = Chatimmi.getSocket()!!
        setupBindings()

        return binding.root
    }

    private fun setupBindings() {
        getTypingStatus()
        getOnlineOfflineStatus()
        chatRepository.getResponseData().observe(activity, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ChatHistoryResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data!!.historyList.size}")

                        group.addAll(getData.data!!.historyList)

                        if (getData.data!!.historyList.isEmpty()) {
                            binding.rvMain.visibility = View.GONE

                            /*  binding.noDataAvailable.visibility = View.VISIBLE
                              viewModel?.clearList()*/
                        } else {
                            viewModel?.getAdapter()?.let {
                                binding.rvMain.visibility = View.VISIBLE
                                //  binding.noDataAvailable.visibility = View.GONE
                                binding.rvMain.adapter = viewModel?.getAdapter()
                                viewModel?.getAdapter()!!.addData(group)

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
        viewModel!!.getAdapterCardObserver().observe(activity, Observer {
            it?.let {
                it.let {

                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("groupName", it.fullName)
                    /*            intent.putExtra("categoryName", it.categoryName)
                                intent.putExtra("subCategoryName", it.subCategoryName)*/
                    intent.putExtra("userId", it.userID)
                    intent.putExtra("avatar", it.avatar)
                    intent.putExtra("emailId", it.email)
                    //intent.putExtra("position", group.indexOf(it))
                    startActivity(intent)
                }

            }
        })

    }

    fun getTypingStatus() {
        Chatimmi.mSocket!!.on("typing") { args ->
            Log.e("typing", args[0].toString())
            val data = args[0] as JSONObject
            try {
                activity.runOnUiThread {
                    val myUserID = data.getString("userID")
                    val oppUserId = data.getString("frontUserID")
                    val typingStatus = data.getInt("is_typing")
                    for (i in 0 until group.size) {

                        if (oppUserId == session.getUserData()!!.data!!.user_details.userID.toString() && myUserID == group[i].userID.toString()) {
                            group[i].typing = typingStatus
                        }

                    }
                    viewModel!!.getAdapter().notifyDataSetChanged()
                }
            } catch (e: Exception) {

            }
        }


    }

    fun getOnlineOfflineStatus() {
        mSocket.on("userStatus", Emitter.Listener { args ->
            Log.e("userStatus", args[0].toString())
            val data = args[0] as JSONObject
            try {
                val isOnline = data.getString("is_online")
                val opponentId = data.getString("userID")


                for (i in 0 until group.size) {
                    if (opponentId == group[i].userID.toString())

                        group[i].isOnline = isOnline.toInt()

                }
                activity.runOnUiThread {
                    viewModel!!.getAdapter().notifyDataSetChanged()


                }


            } catch (e: Exception) {
                Log.d("hjghuhhjh", "Exception:====== ${e.message}")
            }

        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                StudyFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }
}