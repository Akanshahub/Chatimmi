package com.chatimmi.usermainfragment.group.immigration.details

import androidx.lifecycle.ViewModel
import com.chatimmi.R

class ImmigrationDetailsViewModel : ViewModel(){
    private lateinit var adapter: ImmigrationDetailsAdapter
    //  private var viewModel: ImageViewModel? = null
    fun init() {
        adapter = ImmigrationDetailsAdapter(R.layout.single_item_join_member, this)
    }
    fun getAdapter(): ImmigrationDetailsAdapter? {
        return adapter
    }
}