package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_item_create.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*

class ItemCreateActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_create)

        initSpinners()

        button_ItemCreateAdd.setOnClickListener { addItem() }
    }

    fun initSpinners(){
        val spinnerShippingArray = ArrayList<String>()
        spinnerShippingArray.add("Yurtdisi Kargo")
        spinnerShippingArray.add("Keras Kargo")
        spinnerShippingArray.add("BST Kargo")

        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerShippingArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems = spinner_ItemCreateShippingInfo
        sItems.adapter = adapter

        spinner_ItemCreateShippingInfo.setSelection(0)

        val spinnerCategoryArray = ArrayList<String>()
        spinnerCategoryArray.add("Running")
        spinnerCategoryArray.add("Cloths")
        spinnerCategoryArray.add("Fitness")
        spinnerCategoryArray.add("Hiking")
        spinnerCategoryArray.add("Ski")
        spinnerCategoryArray.add("Snowboard")
        spinnerCategoryArray.add("Soccer")
        spinnerCategoryArray.add("Basketball")
        spinnerCategoryArray.add("Swimming")
        spinnerCategoryArray.add("Cycling")
        spinnerCategoryArray.add("Tennis")

        val adapter2 = ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerCategoryArray)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems2=spinner_ItemCreateCategory
        sItems2.adapter=adapter2

        spinner_ItemCreateCategory.setSelection(0)

    }

    fun createuuid(): String {
        val uuid = UUID.randomUUID()
        return uuid.toString()
    }

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

        val itemshipping = spinner_ItemCreateShippingInfo.selectedItem.toString()

        if(editText_ItemCreateStockCount.text.isEmpty()){
            Toast.makeText(this,"Please enter stock of new item", Toast.LENGTH_SHORT).show()
            return
        }
        val itemstock=editText_ItemCreateStockCount.text.toString().toInt()

        val itemCategory = spinner_ItemCreateCategory.selectedItem.toString()

        val uuid = createuuid()

        AsyncUtil{
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(sendNewItemRequest(itemtitle,itemprice,itemdescription,itemshipping,itemstock,itemCategory,uuid)))

            runOnUiThread{
                Toast.makeText(this,responseList[1],Toast.LENGTH_SHORT).show()
            }
            if(responseList[0] == "true"){
                ActivityOpenerUtil.openSellerItemListActivity(this)
                finish()
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    fun sendNewItemRequest(item_title: String, price: Float, description: String, shipping_info: String, stock_count: Int,item_category: String, uuid: String):String{
        return HttpUtil.sendPoststr(UrlParamUtil.createItemToUrlParam(item_title,price,description,shipping_info,stock_count,item_category,uuid), "${getString(R.string.base_url)}/item/add/", SessionUtil.getSessionId(this)!!)
    }

}
