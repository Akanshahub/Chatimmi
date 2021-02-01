package com.chatimmi.helper.cateagoriesdailog

import androidx.lifecycle.ViewModel
import com.chatimmi.R
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.helper.cateagoriesdailog.viewmore.ViewMoreCategoryAdapter

class CateagoriesConnectViewModel: ViewModel(){
    private lateinit var viewMoreCategoryAdapter: ViewMoreCategoryAdapter
    lateinit var commonTaskPerformer: CommonTaskPerformer
    /*    fun init(commonTaskPerformer: CommonTaskPerformer){
            this.commonTaskPerformer=commonTaskPerformer
            marketPlaceAdapter=MarketPlaceAdapter(R.layout.single_market_place,this)
        }*/
    fun init(commonTaskPerformer: CommonTaskPerformer){
        this.commonTaskPerformer=commonTaskPerformer
        viewMoreCategoryAdapter =  ViewMoreCategoryAdapter(R.layout.single_item_view_more, this)
        
    }

    fun onCancelClick() {
        commonTaskPerformer.dismissDialog()
    }
    fun getAdapter(): ViewMoreCategoryAdapter? {
        return viewMoreCategoryAdapter
    }
}