package com.babob.sporcantam.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.recycler_seller_item_layout.view.*

class RecyclerShoppingCartAdapter (var dataset: ArrayList<Item>, var context: Context) :
        RecyclerView.Adapter<RecyclerShoppingCartAdapter.ViewHolder>(){


    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        var textTitle: TextView
        var textPrice: TextView
        var textCount: TextView
        init {
            textTitle = linearLayout.findViewById(R.id.textView_recItemTitle)
            textPrice = linearLayout.findViewById(R.id.textView_recItemPrice)
            textCount = linearLayout.findViewById(R.id.textView_recStockCount)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        // create a new view
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_seller_item_layout, parent, false)
                as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(linearLayout)

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textTitle.text = dataset[position].item_title
        holder.textCount.text = dataset[position].stock_count.toString()
        holder.textPrice.text = dataset[position].price.toString()

        holder.itemView.setOnClickListener {
            Log.d("Recycle adapter", dataset[position].item_title)
            showAlertDialog(dataset[position], position)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size

    private fun deleteItem(item: Item, position: Int){
        AsyncUtil{
            val response = JsonUtil.generalServerResponseToList(HttpUtil.sendPoststr(
                    UrlParamUtil.itemToAddCartParam(item),
                    "${context.getString(R.string.base_url)}/customer/removeFromCart", SessionUtil.getSessionId(context)!!))
            when {
                response.size < 2 -> (context as Activity).runOnUiThread{ Toast.makeText(context, "Cannot connect to the server", Toast.LENGTH_SHORT).show() }
                response[0] == "true" -> (context as Activity).runOnUiThread {
                    Toast.makeText(context, "Item deleted from cart", Toast.LENGTH_SHORT).show()
                    dataset.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, dataset.size)
                }
                else -> (context as Activity).runOnUiThread{ Toast.makeText(context, response[1], Toast.LENGTH_SHORT).show() }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun showAlertDialog(item: Item, position: Int){
        // Initialize a new instance of
        val builder = AlertDialog.Builder(context)

        // Set the alert dialog title
        builder.setTitle("Removing from the cart")

        // Display a message on alert dialog
        builder.setMessage("Do you wanna remove this item ?${item.item_title}")

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("YES"){ dialog, which ->
            // Do something when user press the positive button
            deleteItem(item, position)
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("No"){ dialog, which ->
            Toast.makeText(context,"Not removed", Toast.LENGTH_SHORT).show()
        }


        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()
        // Display the alert dialog on app interface
        dialog.show()
    }

}