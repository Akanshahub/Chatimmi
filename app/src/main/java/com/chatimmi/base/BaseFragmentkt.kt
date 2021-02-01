package com.chatimmi.base

import android.content.Context
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.chatimmi.app.utils.StackSet

class BaseFragmentkt:Fragment() {
    protected val childBackStack = StackSet<Fragment>()
    protected var activity: BaseActivity? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as BaseActivity
        super.onAttach(context)
    }

    protected val currentChildFragment: Fragment?
        protected get() = childBackStack.peek()

    protected fun replaceChildFragment(fragment: Fragment, @IdRes containerId: Int, addToBackStack: Boolean) {
        try {
            val fm = childFragmentManager
            val fragmentName = fragment.javaClass.name
            fm.beginTransaction().replace(containerId, fragment, fragmentName).commit()
            if (addToBackStack) childBackStack.push(fragment)
            activity!!.hideKeyboard()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}