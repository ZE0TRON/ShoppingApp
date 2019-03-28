package com.babob.sporcantam.activity

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Toast
import com.babob.sporcantam.R
import kotlinx.android.synthetic.main.activity_item_create.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ItemCreateActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_create)

        button_ItemCreateAdd.setOnClickListener { }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addItem(){

        if(editText_ItemCreateItemTitle.text.isEmpty()){
            Toast.makeText(this,"Please enter title of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemtitle=editText_ItemCreateItemTitle.text.toString()

        if(editText_ItemCreatePrice.text.isEmpty()){
            Toast.makeText(this,"Please enter price of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemprice=editText_ItemCreatePrice.text.toString().toFloat()

        if(editText_ItemCreateSeller.text.isEmpty()){
            Toast.makeText(this,"Please enter seller of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemseller=editText_ItemCreateSeller.text.toString()

        if(editText_ItemCreatDescription.text.isEmpty()){
            Toast.makeText(this,"Please enter description of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemdescription=editText_ItemCreatDescription.text.toString()

        if(editText_ItemCreateShippingInfo.text.isEmpty()){
            Toast.makeText(this,"Please enter shipping information of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemshipping=editText_ItemCreateShippingInfo.text.toString()

        if(editText_ItemCreateStockCount.text.isEmpty()){
            Toast.makeText(this,"Please enter stock of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemstock=editText_ItemCreateStockCount.text.toString().toInt()

        if(editText_ItemCreatePublishDate.text.isEmpty()){
            Toast.makeText(this,"Please enter publish date of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itempublishdate = LocalDate.parse(editText_ItemCreatePublishDate.toString(), DateTimeFormatter.ISO_DATE)

        sendNewItemRequest(itemtitle,itemprice,itemseller,itemdescription,itemshipping,itemstock,itempublishdate)

    }

    fun sendNewItemRequest(item_title: String, price: Float, seller: String, description: String, shipping_info: String, stock_count: Int, publish_date: LocalDate){

    }

}
