package com.chatimmi.helper.cateagoriesdailog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R

import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.showToast
import com.chatimmi.databinding.ActivityBottomDailongBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CateagoriesConnectBottomDailog : BottomSheetDialogFragment(), CommonTaskPerformer {

    private val TAG = "CateagoriesConnectBottomDailog"
    private var viewModel: CateagoriesConnectViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding: ActivityBottomDailongBinding = DataBindingUtil.inflate(inflater, R.layout.activity_bottom_dailong, container, false)

        viewModel = ViewModelProviders.of(this).get(CateagoriesConnectViewModel::class.java)
        binding.cateagoriesConnectViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel?.init(this)
        return binding.root
    }

    fun show(fragmentManager: FragmentManager?) {
        if (fragmentManager != null) {
            super.show(fragmentManager, TAG)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                val dialog = dialog as BottomSheetDialog?
                val bottomSheet: FrameLayout? =
                        dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                val behavior: BottomSheetBehavior<View?>? =
                        bottomSheet?.let { BottomSheetBehavior.from(it) }
                behavior?.state = BottomSheetBehavior.STATE_EXPANDED
                behavior?.peekHeight = 0
               behavior?.isDraggable = false
            }
        })
    }

    companion object {
        private const val TAG = "RateUsDialog"
        fun newInstance(): CateagoriesConnectBottomDailog {
            val fragment = CateagoriesConnectBottomDailog()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
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
        this.dismiss()
    }

    override fun launchAction() {

    }

    override fun connectClick(userID: Int) {

    }
}

