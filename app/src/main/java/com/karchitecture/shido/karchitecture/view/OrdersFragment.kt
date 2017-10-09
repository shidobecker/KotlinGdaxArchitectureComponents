package com.karchitecture.shido.karchitecture.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karchitecture.shido.karchitecture.adapters.OpenOrderAdapter
import com.karchitecture.shido.karchitecture.datas.Order
import com.karchitecture.shido.karchitecture.datas.model.PriceSideTuple
import com.karchitecture.shido.karchitecture.viewmodel.OrdersViewModel
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by Shido on 01/10/2017.
 */
class OrdersFragment: Fragment() {

    lateinit var viewModel: OrdersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context.recyclerView {
            val openAdapter = OpenOrderAdapter(context)
            adapter = openAdapter
            viewModel.orders.observe(this@OrdersFragment, Observer { priceSideTuple ->
                if (priceSideTuple != null) { //It= List<PriceSideTuple>

                    val bids = priceSideTuple.filter{it.side.equals("buy")}.sortedByDescending { it.price }.take(10)
                    val asks = priceSideTuple.filter {it.side.equals("sell") }.sortedBy { it.price }.take(10)
                    val list = mutableListOf<PriceSideTuple>()
                    list.addAll(asks)
                    list.addAll(bids)

                    openAdapter.openOrders.clear()
                    openAdapter.openOrders.addAll(list)
                    openAdapter.notifyDataSetChanged()
                }
            })
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager.isItemPrefetchEnabled = false //Scrolling caused crashes without it
        }
    }
}