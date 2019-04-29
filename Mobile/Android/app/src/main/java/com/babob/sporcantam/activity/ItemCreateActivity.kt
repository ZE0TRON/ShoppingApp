package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_item_create.*
import java.util.*

class ItemCreateActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_create)


        button_ItemCreateAdd.setOnClickListener { addItem() }
    }

    fun createuuid(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

    //@RequiresApi(Build.VERSION_CODES.O)
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


        val uuid = createuuid()

        AsyncUtil{
            val responseList = JsonUtil.generalServerResponseToList(sendNewItemRequest(itemtitle,itemprice,itemdescription,itemshipping,itemstock,uuid))
            //TODO: Duplicate, fix = okan
            runOnUiThread {
                if(responseList.isEmpty()){
                    Toast.makeText(this, "cannot connect to the server", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this, responseList[1], Toast.LENGTH_SHORT).show()
                    if(responseList[0] == "true"){
                        ActivityOpenerUtil.openSellerItemListActivity(this)
                        finish()
                    }
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }



    fun sendNewItemRequest(item_title: String, price: Float, description: String, shipping_info: String, stock_count: Int, uuid: String):String{
        return HttpUtil.sendPoststr(UrlParamUtil.createItemToUrlParam(item_title,price,description,shipping_info,stock_count,uuid), "${getString(R.string.base_url)}/item/add/", SessionUtil.getSessionId(this)!!)
    }

}
