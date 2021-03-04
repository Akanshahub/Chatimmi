package com.chatimmi.usermainfragment.marketplace

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.marketplace.detials.MarketPlaceDetials

class MarketPlaceViewModel : ViewModel() {
    private lateinit var marketPlaceAdapter: MarketPlaceAdapter
    lateinit var commonTaskPerformer: CommonTaskPerformer

    /*    fun init(commonTaskPerformer: CommonTaskPerformer){
            this.commonTaskPerformer=commonTaskPerformer
            marketPlaceAdapter=MarketPlaceAdapter(R.layout.single_market_place,this)
        }*/
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer
        marketPlaceAdapter = object : MarketPlaceAdapter(R.layout.single_market_place, this) {
            override fun onConnectCallBack() {
                //  commonTaskPerformer.showMsg("Under Development")
                commonTaskPerformer.performAction(MarketPlaceDetials::class.java, null, false)
            }
        }
    }

    fun getAdapter(): MarketPlaceAdapter {
        return marketPlaceAdapter
    }
}