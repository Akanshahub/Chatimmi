package com.chatimmi.helper.joindailong

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseBottomDialog
import com.chatimmi.databinding.ActivityJoinBottomDailongBinding

class JoinBottomDialog : BaseBottomDialog(), CommonTaskPerformer {
    private val TAG = "JoinBottomDialog"
    private var viewModel: JoinViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: ActivityJoinBottomDailongBinding = DataBindingUtil.inflate(inflater, R.layout.activity_join_bottom_dailong, container, false)

        viewModel = ViewModelProviders.of(this).get(JoinViewModel::class.java)
        binding.joinViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel?.init(this)
        return binding.root
    }
    fun show(fragmentManager: FragmentManager?) {
        if (fragmentManager != null) {
            super.show(fragmentManager, TAG)
        }
    }

    companion object {
        private const val TAG = "RateUsDialog"
        fun newInstance(): JoinBottomDialog {
            val fragment = JoinBottomDialog()
            val bundle = Bundle()
            fragment.setArguments(bundle)
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
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
    this.dismiss()
    }
}

