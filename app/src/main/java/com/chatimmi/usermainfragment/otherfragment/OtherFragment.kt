package com.chatimmi.usermainfragment.otherfragment


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentOtherBinding
import com.chatimmi.fragmentchatimmi.ChatimmiActivity
import com.chatimmi.model.LoginRegistrationResponse
import com.chatimmi.model.LogoutResponse
import com.chatimmi.repository.LogoutRepository
import com.chatimmi.repository.SignInRepository
import com.chatimmi.viewmodel.LogoutViewModalFactory
import com.chatimmi.viewmodel.OtherFragmentViewModel
import com.chatimmi.viewmodel.SignInViewModalFactory
import com.chatimmi.views.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.NonCancellable.cancel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OtherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("UNREACHABLE_CODE")
class OtherFragment : BaseFragment(), CommonTaskPerformer {
    lateinit var binding: FragmentOtherBinding
    lateinit var viewModal: OtherFragmentViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentOtherBinding>(inflater, R.layout.fragment_other, container, false)
        val logoutRepository = LogoutRepository()
        val factory = LogoutViewModalFactory(logoutRepository)
        viewModal = ViewModelProviders.of(this, factory).get(OtherFragmentViewModel::class.java)
        binding.otherFragmentViewModel = viewModal
        binding.lifecycleOwner = this
        viewModal.init(this)

        logoutRepository.getLogOutResponseData().observe(activity, androidx.lifecycle.Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {

                        val getData = it.data as LogoutResponse

                        Log.d("fabbb", "onCreate: $getData")

                        val mAlertDialog = AlertDialog.Builder(context)
                       // mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                        mAlertDialog.setTitle("Alert") //set alertdialog title
                        mAlertDialog.setMessage("Are you sure you want to logout?") //set alertdialog message
                        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                            val intent = Intent(activity, SignInActivity::class.java)
                            activity.navigateTo(intent, false)
                        }
                        mAlertDialog.setNegativeButton("No") { dialog, id ->
                            dialog.dismiss()
                        }
                        mAlertDialog.show()


                    }

                    is UIStateManager.Error -> {
                        activity.toastMessage(it.msg, context)
                    }
                    is UIStateManager.Loading -> {

                           if (it.shouldShowLoading) {
                               activity.showLoader()
                           } else {
                               activity.hideLoader()
                           }
                    }
                    else -> {
                    }
                }
            }
        })


        return binding.root


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
        TODO("Not yet implemented")
    }

}