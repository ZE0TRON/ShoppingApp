package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import kotlinx.android.synthetic.main.activity_view_item.*

class ViewItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)

        title = "View Item"

        val item: Item = intent.getSerializableExtra("item") as Item

        fillFields(item)

        button_viewItemAddToCart.setOnClickListener { addToCart(item) }
    }

    fun fillFields(item:Item){
        textView_viewItemDescription.text = item.description
        textView_viewItemTitle.text = item.item_title
        textView_viewItemPriceNum.text = item.price.toString()
        textView_viewItemSeller.text = item.seller
        textView_viewItemStockCount.text = item.stock_count.toString()

    }

    fun addToCart(item:Item){

    }
}
