package com.babob.sporcantam.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.item.Order
import com.babob.sporcantam.utility.ActivityOpenerUtil
import kotlinx.android.synthetic.main.recycler_seller_item_layout.view.*

class RecyclerManipulateItems (var dataset: ArrayList<Item>, var context: Context) :
        RecyclerView.Adapter<RecyclerManipulateItems.ViewHolder>(){


    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        var item_title: TextView
        var price: TextView
        var seller: TextView
        var description: TextView
        var shipping_info: TextView
        var stock_count: TextView
        var category: TextView
        var uuid: TextView
        init {
            item_title = linearLayout.findViewById(R.id.textView33)
            price = linearLayout.findViewById(R.id.textView34)
            seller = linearLayout.findViewById(R.id.textView35)
            description = linearLayout.findViewById(R.id.textView36)
            shipping_info = linearLayout.findViewById(R.id.textView37)
            stock_count = linearLayout.findViewById(R.id.textView38)
            category = linearLayout.findViewById(R.id.textView39)
            uuid = linearLayout.findViewById(R.id.textView40)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        // create a new view
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
                as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(linearLayout)

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.item_title.text = dataset[position].item_title
        holder.price.text = dataset[position].price.toString()
        holder.seller.text = dataset[position].seller
        holder.description.text = dataset[position].description
        holder.shipping_info.text = dataset[position].shipping_info
        holder.stock_count.text = dataset[position].stock_count.toString()
        holder.category.text = dataset[position].category
        holder.uuid.text = dataset[position].uuid



        holder.itemView.setOnClickListener {
            Log.d("Recycle adapter", dataset[position].uuid)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size


}