package com.karchitecture.shido.karchitecture.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karchitecture.shido.karchitecture.R
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.textView

/**
 * Created by Shido on 01/10/2017.
 */
class ChartFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return context.frameLayout{
            backgroundColor = ContextCompat.getColor(context, R.color.mainActivityBG)

            textView{
                backgroundColor = ContextCompat.getColor(context, R.color.white)
                text = "Hello Orders"
            }

        }
    }
}