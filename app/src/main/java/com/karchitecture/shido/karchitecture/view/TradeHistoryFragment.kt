package com.karchitecture.shido.karchitecture.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karchitecture.shido.karchitecture.adapters.TradesAdapter
import com.karchitecture.shido.karchitecture.datas.Trade
import com.karchitecture.shido.karchitecture.extensions.e
import com.karchitecture.shido.karchitecture.viewmodel.TradeHistoryViewModel
import org.jetbrains.anko.recyclerview.v7.recyclerView

/**
 * Created by Shido on 01/10/2017.
 */
class TradeHistoryFragment : Fragment() {

    val RECYCLER_VIEW_ID = 10
    lateinit var tradesAdapter: TradesAdapter
    lateinit var viewModel: TradeHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(TradeHistoryViewModel::class.java)
        tradesAdapter = TradesAdapter(context)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context.recyclerView {
            isFocusableInTouchMode = true
            id = RECYCLER_VIEW_ID
            adapter = tradesAdapter
            //RecyclerView is Observing this method which brings a list of LiveData MatchOrders that we can work with it
            viewModel.trades.observe(this@TradeHistoryFragment, Observer {
                //it == List<MatchOrder>
                if (it != null) {
                    val trades: List<Trade> = it.map { Trade(it.size.toFloat(), it.price.toFloat(), it.time, (it.side == "buy")) }//Mapping to a collection of trades
                    tradesAdapter.trades.clear()
                    tradesAdapter.trades.addAll(trades)
                    tradesAdapter.notifyDataSetChanged()
                }
            })
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager.isItemPrefetchEnabled = false //Scrolling caused crashes without it

        }/*.layoutParams  lparams(matchParent, wrapContent) {
            bottomMargin = dip(20)
            behavior = AppBarLayout.ScrollingViewBehavior() }*/

    }


}