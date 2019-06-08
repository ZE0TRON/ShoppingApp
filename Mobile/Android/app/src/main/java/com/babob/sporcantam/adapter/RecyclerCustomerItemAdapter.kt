package com.babob.sporcantam.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.ActivityOpenerUtil
import com.bumptech.glide.Glide

class RecyclerCustomerItemAdapter (var dataset: ArrayList<Item>, var context: Context) :
        RecyclerView.Adapter<RecyclerCustomerItemAdapter.ViewHolder>(){


    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        var textTitle: TextView
        var textPrice: TextView
        var textCount: TextView
        var imageViewPhoto: ImageView
        init {
            textTitle = linearLayout.findViewById(R.id.textView_recItemTitle)
            textPrice = linearLayout.findViewById(R.id.textView_recItemPrice)
            textCount = linearLayout.findViewById(R.id.textView_recStockCount)
            imageViewPhoto = linearLayout.findViewById(R.id.imageView2)
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

        //TODO: sikinti burada olucak
        val url = "http://134.209.226.138:8080/customer/downloadFile/"+dataset[position].uuid
        Glide.with(context).load(url).into(holder.imageViewPhoto)

        holder.itemView.setOnClickListener {
            Log.d("Recycle adapter", dataset[position].item_title)
            ActivityOpenerUtil.openViewItemActivity(context, dataset[position])
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size


}