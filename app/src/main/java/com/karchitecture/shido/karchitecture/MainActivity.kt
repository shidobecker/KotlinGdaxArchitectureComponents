package com.karchitecture.shido.karchitecture

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toolbar
import com.karchitecture.shido.karchitecture.extensions.e
import com.karchitecture.shido.karchitecture.view.TradeHistoryFragment
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.support.v4.drawerLayout
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {


    private var toolBar: Toolbar? = null

    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildLayout()
        lifecycle.addObserver(GdaxWebSocket()) //Adding a new Observer
    }


    fun buildLayout() {
        val FRAMELAYOUT_ID = 133
        drawer = drawerLayout {

        coordinatorLayout {
            backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)
            fitsSystemWindows = true

            appBarLayout {
                backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.appbar)
                val toolbar = toolbar {
                    title = "GDAX"
                     setNavigationIcon(R.drawable.ic_menu_black_24dp)
                    setTitleTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                    backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)

                }.lparams(width = matchParent, height = dip(50))
                this@MainActivity.setActionBar(toolBar)
                toolbar.setNavigationOnClickListener {
                    drawer.openDrawer(Gravity.LEFT)
                }

            }.lparams(width = matchParent)

            //Fragment
            frameLayout{
                id = FRAMELAYOUT_ID
            }.lparams(width = matchParent, height = matchParent)




            }.lparams(width = matchParent, height = matchParent)


            //Drawer
            val navDrawer = NavDrawer(this@MainActivity, {drawer.closeDrawers()})
            navDrawer.lparams(width = dip(250), height = matchParent){
                gravity = Gravity.START
            }
            this.addView(navDrawer)
        }

            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(FRAMELAYOUT_ID, TradeHistoryFragment())
            fragmentTransaction.commit()

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


