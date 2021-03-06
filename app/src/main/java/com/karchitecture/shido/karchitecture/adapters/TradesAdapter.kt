package com.karchitecture.shido.karchitecture.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.karchitecture.shido.karchitecture.R
import com.karchitecture.shido.karchitecture.datas.Trade
import kotlinx.android.synthetic.main.completed_trades.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.include
import org.jetbrains.anko.textColor

/**
 * Created by Shido on 23/09/2017.
 */


class TradesAdapter(val context: Context) : RecyclerView.Adapter<TradesAdapter.ViewHolder>() {

    val trades = mutableListOf<Trade>()

    override fun getItemCount() = trades.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.include<ConstraintLayout>(R.layout.completed_trades)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(trades[position])
    }


   inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(trade: Trade){
            with(trade){
                itemView.sizeBar.backgroundColor =  if(trade.isBuy) ContextCompat.getColor(context, R.color.green)
                else ContextCompat.getColor(context, R.color.red)
                val sizeofView = if(trade.size < 130) trade.size +1 else 130f
                itemView.sizeBar.layoutParams = ConstraintLayout.LayoutParams(itemView.dip(sizeofView), 0)
                itemView.sizeTextView.text = trade.size.toString().padEnd(10, '0') //Pads the string to the specified [length] at the end with the specified character or space.
                val beforeDec =trade.price.toString().substringBefore(".")
                val afterDec = trade.price.toString().substringAfter(".").padEnd(4, '0')
                itemView.priceTextView.text = "$beforeDec.$afterDec"

                itemView.priceTextView.textColor = if(trade.isBuy) ContextCompat.getColor(context, R.color.green)
                else ContextCompat.getColor(context, R.color.red)
                itemView.timeTextView.text = trade.time.substringAfter("T").substringBefore(".")

            }
        }
    }



}