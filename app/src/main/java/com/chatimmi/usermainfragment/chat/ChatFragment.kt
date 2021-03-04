package com.chatimmi.usermainfragment.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
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

    lateinit var binding: FragmentChatBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        setupBindings(savedInstanceState)

        /*  viewModel?.getAdapterClickObserver()?.observeForever {
              it?.let {
                  if(it){
                      JoinBottomDialog.newInstance().show(childFragmentManager)
                  }
              }
          }*/

        return binding.root
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[ChatViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init()
        }

        binding.chatModel = viewModel
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