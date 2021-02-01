package com.chatimmi.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomDialog : BottomSheetDialogFragment(), OnFocusChangeListener {
    var baseActivity: BaseActivitykt? = null
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as BaseActivitykt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun dismissDialog(tag: String?) {
        hideKeyboard()
        dismiss()
    }

    protected fun hideKeyboard() {
        baseActivity!!.hideKeyboard()
    }

    protected fun setLoading(isLoading: Boolean?) {}

    /*protected fun setLoading(progressBar: ProgressBar, isLoading: Boolean) {
         if (mActivity != null) {
             mActivity!!.setLoading(progressBar, isLoading)
         }
     }*/

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus) {
            hideKeyboard()
        }
    }
}
