package com.babob.sporcantam.adapter

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Order
import com.babob.sporcantam.utility.*

class RecyclerManipulateOrders (var dataset: ArrayList<Order>, var context: Context) :
        RecyclerView.Adapter<RecyclerManipulateOrders.ViewHolder>(){


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

        holder.itemView.setOnClickListener {
            Log.d("Recycle adapter", dataset[position].order_id.toString())
            //ActivityOpenerUtil.openItemView_UpdateActivity(context, dataset[position])

        }

        holder.confirm.setOnClickListener {
            Log.d("Recycle Adapter", dataset[position].order_id.toString())


            AsyncUtil{

                val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(deleteOrderRequest(dataset[position].order_id.toString())))
                Toast.makeText(context,responseList[1],Toast.LENGTH_SHORT).show()
                ActivityOpenerUtil.openManipulateOrdersActivity(context)

            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)


        }

        holder.delete.setOnClickListener {
            Log.d("Recycle Adapter", dataset[position].order_id.toString())


            AsyncUtil{

                val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(confirmOrderRequest(dataset[position].order_id.toString())))

                Toast.makeText(context,responseList[1],Toast.LENGTH_SHORT).show()
                ActivityOpenerUtil.openManipulateOrdersActivity(context)

            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        }


    }

    fun deleteOrderRequest(id:String):String{
        return HttpUtil.sendPoststr(id,"${R.string.base_url}/admin/order/delete",SessionUtil.getSessionId(context)!!)
    }

    fun confirmOrderRequest(id:String):String{
        return HttpUtil.sendPoststr(id,"${R.string.base_url}/admin/sale/confirm",SessionUtil.getSessionId(context)!!)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size


}