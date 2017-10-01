package com.karchitecture.shido.karchitecture

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Shido on 01/10/2017.
 */
class NavDrawer(context: Context, action: () -> Unit): LinearLayout(context) {
    val items = arrayOf("Trade History", "Order Book", "Charts")
    init{
        orientation = VERTICAL
        backgroundColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        view{
            backgroundColor = ContextCompat.getColor(context, R.color.bluelight)
        }.layoutParams = LinearLayout.LayoutParams(matchParent, dip(150))

        items.forEach { name ->
            textView(name){
                textColor = ContextCompat.getColor(context, R.color.white)
                textSize = 22f
                padding = dip(16)
                onClick {
                    action()
                    context.toast(name)
                }
            }.layoutParams = LinearLayout.LayoutParams(matchParent, dip (75))
        }
    }
}