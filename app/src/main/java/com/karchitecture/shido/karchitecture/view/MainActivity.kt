package com.karchitecture.shido.karchitecture.view

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toolbar
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.karchitecture.shido.karchitecture.R
import com.karchitecture.shido.karchitecture.datas.GdaxWebSocket
import com.karchitecture.shido.karchitecture.db
import com.karchitecture.shido.karchitecture.extensions.e
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabItem
import org.jetbrains.anko.support.v4.drawerLayout
import org.json.JSONObject
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {


    val FRAMELAYOUT_ID = 133

    lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildLayout()

        lifecycle.addObserver(GdaxWebSocket()) //Adding a new Observer

        /*  async(CommonPool){
            while(true){
            val bids =db.openOrderDao().getBids()
                e("SIZE ${bids.size},  Best buy: ${bids[0]}")
            val asks = db.openOrderDao().getAsks()
                e("SIZE: ${asks.size}, Best Asks: ${asks[0]}")
            }

        }
*/
    }

    fun buildLayout() {
            coordinatorLayout {
                backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)
                fitsSystemWindows = true

                appBarLayout {
                    val actionBarHeight = context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
                    backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.appbar)

                    toolBar = toolbar {
                        title = "GDAX"
                        setTitleTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                        backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)

                    }.lparams(width = matchParent, height = dip(50)) {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    }

                }.lparams(width = matchParent)

                //Main Fragment
                frameLayout {
                    id = FRAMELAYOUT_ID
                }.lparams(width = matchParent, height = matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }

                //Bottom navigation view
                val bottomNav = include<BottomNavigationView>(R.layout.bottom_nav) {
                    setOnNavigationItemSelectedListener {
                        when(it.itemId){
                            R.id.nav_open_orders -> switchFragments(bottomNavItems[0])
                            R.id.nav_open_trade -> switchFragments(bottomNavItems[1])
                            R.id.nav_chart -> switchFragments(bottomNavItems[2])
                        }
                        true
                    }

                }.lparams {
                    gravity = Gravity.BOTTOM
                }


            }


        switchFragments(bottomNavItems[0])

    }





/*    fun switchFragments(entry: NavDrawerEntry) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(FRAMELAYOUT_ID, entry.fragment)
        fragmentTransaction.commit()
        toolBar.title = entry.title
        invalidateOptionsMenu()
    }*/
    fun switchFragments(entry: BottomNavEntry) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(FRAMELAYOUT_ID, entry.fragment)
        fragmentTransaction.commit()
        toolBar.title = entry.title
        invalidateOptionsMenu()
    }


    fun buildDrawerLayout(){
        //lateinit var drawer: DrawerLayout

        /*drawer = drawerLayout {

            coordinatorLayout {
                backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)
                fitsSystemWindows = true

                appBarLayout {
                    val actionBarHeight = context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
                    backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.appbar)

                    toolBar = toolbar {
                        title = "GDAX"
                        setNavigationIcon(R.drawable.ic_menu_black_24dp)
                        setTitleTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                        backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.mainActivityBG)

                    }.lparams(width = matchParent, height = dip(50)) {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                    }

                    this@MainActivity.setActionBar(toolBar)
                    toolBar.setNavigationOnClickListener {
                        drawer.openDrawer(Gravity.LEFT)
                    }

                }.lparams(width = matchParent)

                //Main Fragment
                frameLayout {
                    id = FRAMELAYOUT_ID
                }.lparams(width = matchParent, height = matchParent) {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }

                val bottomNav = include<BottomNavigationView>(R.layout.bottom_nav).lparams{
                    gravity = Gravity.BOTTOM
                }

            }.lparams(width = matchParent, height = matchParent)


            //Drawer
            val navDrawer = NavDrawer(this@MainActivity, action = { navEntry ->
                drawer.closeDrawers()
                //switchFragments(navEntry)
            }
            )
            navDrawer.lparams(width = dip(250), height = matchParent) {
                gravity = Gravity.START
            }
            this.addView(navDrawer)
        }
        switchFragments(bottomNavItems[0])
*/
    }



    override fun onDestroy() {
        super.onDestroy()
    }


}


