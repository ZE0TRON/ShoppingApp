package com.babob.sporcantam.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Order
import com.babob.sporcantam.utility.ActivityOpenerUtil

class RecyclerOrderHistoryAdapter (var dataset: ArrayList<Order>, var context: Context) :
        RecyclerView.Adapter<RecyclerOrderHistoryAdapter.ViewHolder>(){

    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        var customerEmail: TextView
        var orderId:TextView
        var confirm:Button
        var delete:Button
        init {
            customerEmail = linearLayout.findViewById(R.id.textView_order_layout_customer_email)
            orderId = linearLayout.findViewById(R.id.textView_ManipulateOrders_OrderId)
            confirm = linearLayout.findViewById(R.id.button_OrderLayout_OrderConfirm)
            delete = linearLayout.findViewById(R.id.button_OrderLayout_DeleteOrder)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        // create a new view
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_layout, parent, false)
                as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(linearLayout)

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.customerEmail.text = dataset[position].customer_email
        holder.orderId.text = dataset[position].order_id.toString()
        // making admin features invisible
        holder.confirm.visibility = View.GONE
        holder.delete.visibility = View.GONE

        holder.itemView.setOnClickListener {
            Log.d("Recycle adapter", dataset[position].order_id)
            ActivityOpenerUtil.openOrderHistoryItemListActivity(context,dataset[position].order_id)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size


}