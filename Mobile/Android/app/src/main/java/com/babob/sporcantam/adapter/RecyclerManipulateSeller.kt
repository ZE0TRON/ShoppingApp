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
import com.babob.sporcantam.item.Seller
import com.babob.sporcantam.utility.ActivityOpenerUtil
import kotlinx.android.synthetic.main.recycler_seller_item_layout.view.*

class RecyclerManipulateSeller (var dataset: ArrayList<Seller>, var context: Context) :
        RecyclerView.Adapter<RecyclerManipulateSeller.ViewHolder>(){


    class ViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout){
        var firstName: TextView
        var lastName: TextView
        var address: TextView
        var IBAN: TextView
        var phoneNumber: TextView
        init {
            firstName = linearLayout.findViewById(R.id.textView47)
            lastName = linearLayout.findViewById(R.id.textView48)
            address = linearLayout.findViewWithTag(R.id.textView49)
            IBAN = linearLayout.findViewWithTag(R.id.textView50)
            phoneNumber = linearLayout.findViewWithTag(R.id.textView51)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {

        // create a new view
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.seller_layout, parent, false)
                as LinearLayout
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(linearLayout)

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.firstName.text = dataset[position].firstName
        holder.lastName.text = dataset[position].lastName
        holder.address.text = dataset[position].address
        holder.IBAN.text = dataset[position].IBAN
        holder.phoneNumber.text = dataset[position].phoneNumber


        holder.itemView.setOnClickListener {
            Log.d("Recycle adapter", dataset[position].firstName)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size


}