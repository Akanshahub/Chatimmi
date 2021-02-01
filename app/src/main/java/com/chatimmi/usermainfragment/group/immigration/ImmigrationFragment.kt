package com.chatimmi.usermainfragment.group.immigration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentImmigrationBinding

import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupActivity
import com.chatimmi.helper.joindailong.JoinBottomDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ImmigrationFragment : BaseFragment(),CommonTaskPerformer {
    private var viewModel: GroupViewModel? = null

    lateinit var binding: FragmentImmigrationBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_immigration,container,false)
        viewModel = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        binding.model = viewModel
        setupBindings(savedInstanceState)
        viewModel?.getAdapterClickObserver()?.observeForever {
            it?.let {
                if(it){
                  //  JoinBottomDialog.newInstance().show(childFragmentManager)
                    val joinBottomDialog=JoinBottomDialog()
                    joinBottomDialog.isCancelable=false
                    joinBottomDialog.show(childFragmentManager)
                }
            }
        }
        binding.filter.setOnClickListener {
            val intent = Intent(getActivity(), FilterGroupActivity::class.java)
            //listener?.onClick(AlbumsData)
            intent.putExtra("dd", "ff")
            getActivity()?.startActivity(intent)
        }
        return binding.root
    }
    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[GroupViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
        }
        binding.model = viewModel
    }
    companion  object{
        @JvmStatic
        fun newInstance(param1: String) =
                ImmigrationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }

    override fun <T> performAction(clazz: Class<T>) {
        Intent(requireContext(), clazz).apply {
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {
        requireContext().showToast(msg)
    }

    override fun dismissDialog() {
    }
}

