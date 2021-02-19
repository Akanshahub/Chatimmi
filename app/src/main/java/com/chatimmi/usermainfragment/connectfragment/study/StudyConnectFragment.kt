package com.chatimmi.usermainfragment.connectfragment.study

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.app.utils.showToast
import com.chatimmi.base.BaseFragment
import com.chatimmi.databinding.FragmentStudyConnectBinding
import com.chatimmi.retrofitnetwork.ApiCallback
import com.chatimmi.usermainfragment.connectfragment.filter.filtercategoryconnect.FilterActivity
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ConsultantListResponce
import com.chatimmi.usermainfragment.connectfragment.immigrationconnect.ImmigrationConnectRepositary

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudyConnectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudyConnectFragment : BaseFragment(), CommonTaskPerformer,ApiCallback.ConnectConsultentList {
    private var viewModel: StudyConnectViewModel? = null
    lateinit var binding: FragmentStudyConnectBinding
    private var userId = 0
    lateinit var repo: ImmigrationConnectRepositary
    var list = ArrayList<ConsultantListResponce.Data.Consultant>()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study_connect, container, false)
        repo = ImmigrationConnectRepositary(activity,this)
        setupBindings(savedInstanceState)
        binding.filter.setOnClickListener {
            val intent = Intent(getActivity(), FilterActivity::class.java)
            intent.putExtra("dd", "ff")
            getActivity()?.startActivity(intent)
        }
        return binding.root
    }
    /*   private fun setupBindings(savedInstanceState: Bundle?) {
           viewModel = ViewModelProviders.of(this)[StudyConnectViewModel::class.java]
           if (savedInstanceState == null) {
               viewModel?.init(this)
           }
           binding.studyModel = viewModel
       }*/

    private fun setupBindings(savedInstanceState: Bundle?) {
        val factory = StudyConnectViewModelFactory(repo)
        viewModel = ViewModelProviders.of(this, factory)[StudyConnectViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
        }
        binding.studyModel = viewModel
        intiViews()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
                StudyConnectFragment().apply {
                    arguments = Bundle().apply {
                    }
                }
    }

    private fun observe() {
        repo.getResponseData().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as ConsultantListResponce
                        list.addAll(getData.data.consultantList)
                        if (getData.data.consultantList.size == 0) {
                            /* binding.rvMain.visibility = View.GONE
                             binding.noDataAvailable.visibility = View.VISIBLE
                             viewModel?.clearList()*/
                        } else {
                            viewModel?.getAdapter()?.let {
                                binding.rvMain.visibility = View.VISIBLE
                                binding.rvMain.adapter = viewModel?.getAdapter()
                                viewModel?.getAdapter()!!.addData(list)
                                viewModel?.getAdapter()!!.notifyDataSetChanged()
                            }
                        }


                    }
                    is UIStateManager.Error -> {
                        showMsg(it.msg)
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
        repo.getResponseDataConnectClick().observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val position = list.indexOf(list.filter { it.userID.equals(this.userId) }[0])
                        list[position].isConnect = 1
                        viewModel!!.getAdapter()!!.addData(list)
                    }
                    is UIStateManager.Error -> {
                        showMsg(it.msg)
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

      binding.itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, R.color.primary_100))
        binding.itemsswipetorefresh.setColorSchemeColors(Color.WHITE)

        binding.itemsswipetorefresh.setOnRefreshListener {
            list.clear()
            repo.callConsultantListApi("3")
            viewModel!!.getAdapter()!!.notifyDataSetChanged()
            binding.itemsswipetorefresh.isRefreshing = false
        }
    }

    private fun intiViews() {
        viewModel?.callConsultantConnectListApi()
        observe()
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

    override fun connectClick(userID: Int) {
        this.userId = userID
        viewModel?.setConnectConsultantAPI(userID.toString())
    }

    override fun onSuccessLogin(mConsultantListResponce: ConsultantListResponce) {

    }

    override fun onShowBaseLoader() {

    }

    override fun onHideBaseLoader() {

    }

    override fun onError(errorMessage: String) {
        Toast.makeText(activity,errorMessage,Toast.LENGTH_LONG).show()
    }
}