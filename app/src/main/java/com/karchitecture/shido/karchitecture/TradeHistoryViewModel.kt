package com.karchitecture.shido.karchitecture

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.karchitecture.shido.karchitecture.datas.model.MatchOrder
import com.karchitecture.shido.karchitecture.extensions.e

/**
 * Created by Shido on 30/09/2017.
 */
class TradeHistoryViewModel(app: Application) : AndroidViewModel(app) {

   val trades: LiveData<List<MatchOrder>> = MutableLiveData<List<MatchOrder>>()
        get() {
            if(field.value == null){
                e("LOADED TRADED ORDERS")
                field = db.matchOrderDao().loadMatchOrdersSync()
            }
            return field
        }

}