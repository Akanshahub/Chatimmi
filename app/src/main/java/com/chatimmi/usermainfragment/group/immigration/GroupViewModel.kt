package com.chatimmi.usermainfragment.group.immigration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chatimmi.R.layout.single_item_grouplist
import com.chatimmi.app.utils.CommonTaskPerformer
import com.chatimmi.usermainfragment.group.immigration.details.ImmigrationDetailsActivity

class GroupViewModel : ViewModel(){
    private lateinit var groupAdapter: GroupAdapter
    /*
    var itemObserver = MutableLiveData<Boolean>()

    fun getitemCilckObserver() = itemObserver as LiveData<Boolean>*/
    var itemClickObserver = MutableLiveData<Boolean>()
    fun getAdapterClickObserver() = itemClickObserver as LiveData<Boolean>
    lateinit var commonTaskPerformer: CommonTaskPerformer
    fun init(commonTaskPerformer: CommonTaskPerformer) {
        this.commonTaskPerformer = commonTaskPerformer;


        groupAdapter = object : GroupAdapter(single_item_grouplist, this){
            override fun onitemBack() {
                commonTaskPerformer.performAction(ImmigrationDetailsActivity::class.java)

            }
            override fun onLockCallBack() {
                itemClickObserver.value=true
            }

        }
    }

    fun getAdapter(): GroupAdapter? {
        return groupAdapter
    }
}