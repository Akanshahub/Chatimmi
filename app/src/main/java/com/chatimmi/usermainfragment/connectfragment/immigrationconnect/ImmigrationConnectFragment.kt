package com.chatimmi.usermainfragment.connectfragment.immigrationconnect

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentImmigrationconnect2Binding
import com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect.FilterActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImmigrationConnectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImmigrationConnectFragment : BaseFragment(), CommonTaskPerformer {
    private var viewModel: ImmigrationConnectViewModel? = null

    lateinit var binding: FragmentImmigrationconnect2Binding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_immigrationconnect2, container, false)
        setupBindings(savedInstanceState)
        binding.filter.setOnClickListener {
            val intent = Intent(getActivity(), FilterActivity::class.java)
            intent.putExtra("dd", "ff")
            getActivity()?.startActivity(intent)
        }
        return binding.root
    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this)[ImmigrationConnectViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
        }
        binding.studyModel = viewModel
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                ImmigrationConnectFragment().apply {
                    arguments = Bundle().apply {
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

    override fun launchAction() {

    }
}