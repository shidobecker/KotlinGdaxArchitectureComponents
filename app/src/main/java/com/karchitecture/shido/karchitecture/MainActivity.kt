package com.karchitecture.shido.karchitecture

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toolbar
import com.karchitecture.shido.karchitecture.extensions.e
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TradeHistoryViewModel

    private var toolBar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TradeHistoryViewModel::class.java)
        buildLayout()
        lifecycle.addObserver(GdaxWebSocket()) //Adding a new Observer
    }


    fun buildLayout() {
        val RECYCLER_VIEW_ID = 10
        val myAdapter = MyAdapter(this)

        coordinatorLayout {
            backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)
            fitsSystemWindows = true

            appBarLayout {
                backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.appbar)
                 toolBar = toolbar {
                    title = "GDAX"
                     setNavigationIcon(R.drawable.ic_menu_black_24dp)
                    setTitleTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                    backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)
                }.lparams(width = matchParent, height = dip(50))
                this@MainActivity.setActionBar(toolBar)
            }.lparams(width = matchParent)

                recyclerView {
                    isFocusableInTouchMode = true
                    id = RECYCLER_VIEW_ID
                    adapter = myAdapter
                    //RecyclerView is Observing this method which brings a list of LiveData MatchOrders that we can work with it
                    viewModel.trades.observe(this@MainActivity, Observer {
                        e(it)
                        //it == List<MatchOrder>
                        if (it != null) {
                            val trades: List<Trade> = it.map { Trade(it.size.toFloat(), it.price.toFloat(), it.time, (it.side == "buy")) }//Mapping to a collection of trades
                            myAdapter.trades.clear()
                            myAdapter.trades.addAll(trades)
                            myAdapter.notifyDataSetChanged()
                        }
                    })
                    layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    layoutManager.isItemPrefetchEnabled = false //Scrolling caused crashes without it

                }.lparams(matchParent, wrapContent) {
                    bottomMargin = dip(20)
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }

            }
        }


fun deleteRoom() {
    //Deleting all data before
    thread {
        db.openOrderDao().delete(db.openOrderDao().getAll())
        db.receivedOrderDao().delete(db.receivedOrderDao().getAll())
        db.matchOrderDao().delete(db.matchOrderDao().getAll())
        db.doneOrderDao().delete(db.doneOrderDao().getAll())
        db.changeOrderDao().delete(db.changeOrderDao().getAll())

    }
}

fun logDatabase() {
    //Database access can't be in main thread since it may potentially lock the UI for a long period of time.
    async(CommonPool) {
        db.changeOrderDao().getAll().forEach { e(it) }
        db.receivedOrderDao().getAll().forEach { e(it) }
        db.matchOrderDao().getAll().forEach { e(it) }
        db.openOrderDao().getAll().forEach { e(it) }
        db.doneOrderDao().getAll().forEach { e(it) }
    }
}


override fun onDestroy() {
    super.onDestroy()
}


}


