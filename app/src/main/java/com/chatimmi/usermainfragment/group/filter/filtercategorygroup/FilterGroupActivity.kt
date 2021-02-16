package com.chatimmi.usermainfragment.group.filter.filtercategorygroup


import CustomCategoryList
import android.content.Intent
import android.os.Build
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chatimmi.Chatimmi.Companion.groupFilterResponse
import com.chatimmi.R
import com.chatimmi.app.pref.Session
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.app.utils.UIStateManager
import com.chatimmi.base.BaseActivity
import com.chatimmi.databinding.ActivityFilterGroupBinding
import com.chatimmi.usermainfragment.group.filter.filtersubcategorygroup.FilterSubCategoryGroupActivity
import com.chatimmi.usermainfragment.group.immigration.SearchResponse
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class FilterGroupActivity : BaseActivity(), CommonTaskPerformer {
    lateinit var session: Session
    private var binding: ActivityFilterGroupBinding? = null
    private var viewModel: FilterGroupViewModel? = null
    var categoryId: String = ""
    var subCategoryId = ""
    var groupby = ""
    var searchResponse = SearchResponse()
    lateinit var group: ArrayList<GroupFilterResponse.Data.Category>
    lateinit var groupFilterRepository: GroupFilterRepository
    var list = ArrayList<GroupFilterResponse.Data.Category.Subcategory>()

    /*companion object {
        var customCategoryList = mutableListOf<CustomCategoryList>()
    }*/

    var position = GroupFilterResponse.Data.Category()
    var count: Int = -1

    var mSubCategory: List<String>? = null
    var mCategory: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter_group)
        groupFilterRepository = GroupFilterRepository(activity)
        val factory = GroupFilterViewModelFactory(groupFilterRepository)
        viewModel = ViewModelProviders.of(this, factory)[FilterGroupViewModel::class.java]
        binding!!.model = viewModel
        group = ArrayList()
        session = Session(this)

        viewModel?.init(this)
        // setupBindings(savedInstanceState)
        binding!!.backButton.setOnClickListener {
            if (searchResponse == null) {
                setResult(RESULT_CANCELED)
                finish()
            } else {
                onBackPressed()
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //var count = GroupFilterResponse.Data.Category()
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        if (intent.getParcelableExtra<SearchResponse>("groupName") != null) {
            searchResponse = intent.getParcelableExtra<SearchResponse>("groupName")!!
        }


        if (!searchResponse.category.isNullOrEmpty() && !searchResponse.subcategory.isNullOrEmpty()) {

            mCategory = listOf(*searchResponse.category!!.trim().split(",").toTypedArray())

            mSubCategory = listOf(*searchResponse.subcategory!!.trim().split(",").toTypedArray())

            Log.d("TAG", "onCreate: $mCategory $mSubCategory")
        }



        groupFilterRepository.getResponseData().observe(this, Observer {
            it?.let {
                when (it) {
                    is UIStateManager.Success<*> -> {
                        val getData = it.data as GroupFilterResponse
                        Log.d("bnjnknk", "setupBindings: ${getData.data!!.category.size}")
                        group.addAll(getData.data!!.category)

                        Log.d("bnjnknk", "groupxzxz: ${group}")
                        if (searchResponse.group_scope == "2") {
                            binding!!.cbPrivate.isChecked = true
                            binding!!.cbPublic.isChecked = false
                        } else if (searchResponse.group_scope == "1") {
                            binding!!.cbPublic.isChecked = true
                            binding!!.cbPrivate.isChecked = false
                        } else if (searchResponse.group_scope == "3") {
                            binding!!.cbPrivate.isChecked = true
                            binding!!.cbPublic.isChecked = true
                        }
                        groupFilterResponse = getData


                        if (mCategory != null) {

                            for (i in 0 until getData.data!!.category.size) {
                                var count = 0
                                for (j in 0 until mCategory!!.size) {

                                    if (getData.data!!.category[i].categoryID!!.toString().equals(mCategory!![j])) {

                                        for (k in 0 until getData.data!!.category[i].subcategories.size) {

                                            for (l in mSubCategory!!.indices) {
                                                if (getData.data!!.category[i].subcategories[k].categoryID!!.toString().equals(mSubCategory!![l])) {
                                                    getData.data!!.category[i].subcategories[k].isselected = true
                                                    count += 1
                                                }
                                            }
                                        }
                                    }


                                }
                                getData.data!!.category[i].count = count
                            }

                        }


                        /*for (element in getData.data!!.category) {
                            val categoryId = element
                            for (j in 0 until customCategoryList.size) {
                                val saveCatId = customCategoryList[j]
                                if (categoryId.categoryID == saveCatId.categoryID) {
                                    getData.data!!.category[j].isSelect = true
                                }
                            }
                        }*/
                        viewModel?.getAdapter()?.let {
                            binding!!.rvMain.adapter = viewModel?.getAdapter()
                            viewModel?.getAdapter()!!.addData(getData.data!!.category)
                            viewModel?.getAdapter()!!.notifyDataSetChanged()
                        }
                    }
                    is UIStateManager.Error -> {
                        showMsg(it.msg)
                    }
                    is UIStateManager.Loading -> {
                        if (it.shouldShowLoading) {
                            showLoader()
                        } else {
                            hideLoader()
                        }

                    }
                    else -> {

                    }
                }
            }
        })
        binding!!.clearAll.setOnClickListener {
            for (i in 0 until group.size) {
                group[i].count = 0
                for (element in group[i].subcategories) {
                    element.isselected = false
                }
                viewModel!!.getAdapter()!!.notifyDataSetChanged()
            }
            searchResponse = SearchResponse()
            mSubCategory = null
            mCategory = null
            binding!!.cbPrivate.isChecked = true
            binding!!.cbPublic.isChecked = true
        }
        binding!!.btnSignup.setOnClickListener() {
            for (j in 0 until group.size) {
                if (group[j].count!! > 0) {
                    categoryId = categoryId + group[j].categoryID.toString() + ","
                    for (i in group[j].subcategories.indices) {
                        if (group[j].subcategories[i].isselected!!) {
                            subCategoryId = subCategoryId + group[j].subcategories[i].categoryID + ","
                        }
                    }
                }
            }

            // Log.d("TAG", "onCreate: " + categoryId + "    " + subCategoryId)

            if (binding!!.cbPrivate.isChecked && binding!!.cbPublic.isChecked) {
                groupby = "3"
                // return@setOnClickListener
            } else if (binding!!.cbPrivate.isChecked) {
                groupby = "2"
                //  return@setOnClickListener
            } else if (binding!!.cbPublic.isChecked) {
                groupby = "1"
                //return@setOnClickListener
            }


            val intent = Intent()
            searchResponse.group_scope = groupby

            if (categoryId.isNotEmpty()) {
                searchResponse.category = categoryId.substring(0, categoryId.length - 1)
                searchResponse.subcategory = subCategoryId.substring(0, subCategoryId.length - 1)
            }



            intent.putExtra("searchResult", searchResponse)
            Log.d("TAG", "onCreate: $searchResponse")
            setResult(RESULT_OK, intent)
            finish()
        }





        viewModel?.getAdapterClickObserver()?.observe(this, Observer {
            viewModel?.itemValueObserver
            val intent = Intent(this, FilterSubCategoryGroupActivity::class.java)
            intent.putExtra("parent_category", it)
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivityForResult(intent, 1)

        })
        binding!!.cbPublic.isChecked = true
        binding!!.cbPrivate.isChecked = true



        binding!!.llPrivate.setOnClickListener {
            if (binding!!.cbPublic.isChecked) {
                binding!!.cbPrivate.isChecked = !binding!!.cbPrivate.isChecked
            } else {
                binding!!.cbPrivate.isChecked = true
            }
        }

        binding!!.llPublic.setOnClickListener {
            if (binding!!.cbPrivate.isChecked) {
                binding!!.cbPublic.isChecked = !binding!!.cbPublic.isChecked
            } else {
                binding!!.cbPublic.isChecked = true
            }

        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 1 && resultCode == RESULT_OK) {
                list = data?.getParcelableArrayListExtra("list")!!
                position = data.getParcelableExtra("position")!!
                val filter = list.filter { it.isselected!! }.count()
                var finalPosition = -1
                for (i in 0 until group.size) {
                    if (group[i].categoryID!! == position.categoryID) {
                        finalPosition = i
                        Log.d("TAG", "onActivityResult: $filter   $finalPosition")
                        viewModel!!.updateList(finalPosition, filter, list)
                        group[finalPosition].subcategories = list
                        break
                    }

                    /*if(searchResponse==null){
                         position.subcategories.i=true
                     }*/
                }


            }
        } catch (ex: Exception) {
            Toast.makeText(this@FilterGroupActivity, ex.toString(),
                    Toast.LENGTH_SHORT).show()
        }

    }


    override fun <T> performAction(clazz: Class<T>) {
        Intent(this, clazz).apply {
            startActivity(this)
        }
    }

    override fun showMsg(msg: String) {

    }

    override fun dismissDialog() {

    }

    override fun launchAction() {

    }

    override fun connectClick(userID: Int) {

    }
}