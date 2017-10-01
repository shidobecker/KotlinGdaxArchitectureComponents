package com.karchitecture.shido.karchitecture.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import com.karchitecture.shido.karchitecture.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Shido on 01/10/2017.
 */
val items = arrayOf<NavDrawerEntry>(NavDrawerEntry("Trade History", TradeHistoryFragment()),
        NavDrawerEntry("Order Book", OrdersFragment()),
        NavDrawerEntry("Charts", ChartFragment()))

class NavDrawer(context: Context, action: (navEntry: NavDrawerEntry) -> Unit): LinearLayout(context) {

    init{
        orientation = VERTICAL
        backgroundColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)

        view{
            backgroundColor = ContextCompat.getColor(context, R.color.bluelight)
        }.layoutParams = LinearLayout.LayoutParams(matchParent, dip(150))

        items.forEach { navEntry ->
            textView(navEntry.title){
                textColor = ContextCompat.getColor(context, R.color.white)
                textSize = 22f
                padding = dip(16)
                onClick {
                    action(navEntry)
                    context.toast(navEntry.title)
                }
            }.layoutParams = LinearLayout.LayoutParams(matchParent, dip (75))
        }
    }
}