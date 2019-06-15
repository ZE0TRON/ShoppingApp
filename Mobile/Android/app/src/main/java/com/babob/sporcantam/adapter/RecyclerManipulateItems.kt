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
        var imageViewPhoto: ImageView
        init {
            item_title = linearLayout.findViewById(R.id.textView_item_layout_item_title)
            price = linearLayout.findViewById(R.id.textView_item_layout_price)
            seller = linearLayout.findViewById(R.id.textView_item_layout_seller)
            description = linearLayout.findViewById(R.id.textView_item_layout_description)
            shipping_info = linearLayout.findViewById(R.id.textView_item_layout_shipping_info)
            stock_count = linearLayout.findViewById(R.id.textView_item_layout_stock_count)
            category = linearLayout.findViewById(R.id.textView_item_layout_category)
            uuid = linearLayout.findViewById(R.id.textView_item_layout_uuid)
            imageViewPhoto = linearLayout.findViewById(R.id.imageView_ItemLayoutPhoto)
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

        //TODO: sikinti burada olucak
        val url = "http://134.209.226.138:8080/customer/downloadFile/"+dataset[position].uuid
        Glide.with(context).load(url).into(holder.imageViewPhoto)


        holder.itemView.setOnClickListener {
            Log.d("Recycle adapter", dataset[position].uuid)
            ActivityOpenerUtil.openItemView_UpdateActivity(context, dataset[position])
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size


}