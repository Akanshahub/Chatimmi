package com.chatimmi.usermainfragment.group.study
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
import com.chatimmi.databinding.FragmentStudyBinding
import com.chatimmi.usermainfragment.group.filter.filtercategorygroup.FilterGroupActivity
import com.chatimmi.helper.joindailong.JoinBottomDialog

class StudyFragment : BaseFragment(),CommonTaskPerformer {
    private var viewModel: StudyViewModel? = null

    lateinit var binding: FragmentStudyBinding
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_study,container,false)
        setupBindings(savedInstanceState)

        viewModel?.getAdapterClickObserver()?.observeForever {
            it?.let {
                if(it){
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
        viewModel = ViewModelProviders.of(this)[StudyViewModel::class.java]
        if (savedInstanceState == null) {
            viewModel?.init(this)
        }

        binding.studyModel = viewModel
    }
    companion object{
        @JvmStatic
        fun newInstance(param1: String) =
                StudyFragment().apply {
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
}