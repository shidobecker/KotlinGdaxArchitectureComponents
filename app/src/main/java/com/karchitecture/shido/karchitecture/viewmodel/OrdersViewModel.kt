package com.karchitecture.shido.karchitecture.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.karchitecture.shido.karchitecture.datas.model.OpenOrder
import com.karchitecture.shido.karchitecture.datas.model.PriceSideTuple
import com.karchitecture.shido.karchitecture.datas.model.PriceTuple
import com.karchitecture.shido.karchitecture.db

/**
 * Created by Shido on 01/10/2017.
 */
class OrdersViewModel(ctx: Application) : AndroidViewModel(ctx) {
    val orders: LiveData<List<PriceSideTuple>> = MutableLiveData<List<PriceSideTuple>>()
    get() {
        if(field.value == null){ //Using when the device changes to landscape
            field = db.openOrderDao().loadOpenOrdersSync()
        }
        return field
    }

}