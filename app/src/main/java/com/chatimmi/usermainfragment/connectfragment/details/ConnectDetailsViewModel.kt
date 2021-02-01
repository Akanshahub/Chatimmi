package com.chatimmi.usermainfragment.connectfragment.details

import androidx.lifecycle.ViewModel
import com.chatimmi.R

class ConnectDetailsViewModel : ViewModel(){
    private lateinit var adapter: ConnectDetailsAdapter
    //  private var viewModel: ImageViewModel? = null
    fun init() {
        adapter = ConnectDetailsAdapter(R.layout.single_item_connect_details, this)
    }
    fun getAdapter(): ConnectDetailsAdapter? {
        return adapter
    }

}