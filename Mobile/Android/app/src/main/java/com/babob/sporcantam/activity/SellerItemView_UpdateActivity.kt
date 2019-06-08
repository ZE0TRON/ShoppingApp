package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_item_create.*
import kotlinx.android.synthetic.main.activity_seller_item_view_update.*
import java.util.ArrayList

class SellerItemView_UpdateActivity : AppCompatActivity() {

    var update_save = true
    /*
    true-> view page
    false-> update page
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_item_view_update)


        val item: Item = intent.getSerializableExtra("item") as Item

        initSpinners(item)
        fillFields(item)

        button_ItemView_UpdateActivityUpdateSave.setOnClickListener { helper(item.uuid) }
        button_ItemView_UpdateActivityDelete.setOnClickListener {
            AsyncUtil{ delete(item.uuid) }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
             }



    }

    fun initSpinners(item:Item){
        val spinnerShippingArray = ArrayList<String>()
        spinnerShippingArray.add("Yurtdisi Kargo")
        spinnerShippingArray.add("Keras Kargo")
        spinnerShippingArray.add("BST Kargo")

        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerShippingArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems = spinner_ItemView_UpdateShipping
        sItems.adapter = adapter

        spinner_ItemView_UpdateShipping.setSelection(spinnerShippingArray.indexOf(item.shipping_info))

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
        val sItems2=spinner_ItemView_UpdateCategory
        sItems2.adapter=adapter2

        Log.d("SellerItemView", item.category)
        Log.d("SellerItemView", (spinnerCategoryArray.indexOf(item.category).toString()))

        sItems2.setSelection(spinnerCategoryArray.indexOf(item.category))

    }

    fun fillFields(item: Item){
        editText_ItemView_UpdateActivityItemTitle.setText(item.item_title)
        editText_ItemView_UpdateActivityDescription.setText(item.description)
        editText_ItemView_UpdateActivityStock.setText(item.stock_count.toString())
        editText_ItemView_UpdateActivityPrice.setText(item.price.toString())
        editText_ItemView_UpdateActivitySeller.setText(item.seller)
    }

    fun changeEnable() {

        if (button_ItemView_UpdateActivityUpdateSave.text=="Update")
            button_ItemView_UpdateActivityUpdateSave.text="Save"
        else
            button_ItemView_UpdateActivityUpdateSave.text="Update"

        button_ItemView_UpdateActivityDelete.isEnabled=!button_ItemView_UpdateActivityDelete.isEnabled

        editText_ItemView_UpdateActivityItemTitle.isEnabled=!editText_ItemView_UpdateActivityItemTitle.isEnabled
        editText_ItemView_UpdateActivityDescription.isEnabled=!editText_ItemView_UpdateActivityDescription.isEnabled
        editText_ItemView_UpdateActivityStock.isEnabled=!editText_ItemView_UpdateActivityStock.isEnabled
        editText_ItemView_UpdateActivityPrice.isEnabled=!editText_ItemView_UpdateActivityPrice.isEnabled
        //editText_ItemView_UpdateActivityShipping.isEnabled=!editText_ItemView_UpdateActivityShipping.isEnabled
        spinner_ItemView_UpdateShipping.isEnabled!=spinner_ItemView_UpdateShipping.isEnabled
        spinner_ItemCreateCategory.isEnabled!=spinner_ItemView_UpdateCategory.isEnabled


    }


    fun helper(uuid:String){
        if(update_save){
            changeEnable()
            this.update_save =!this.update_save
        }
        else{

            if( editText_ItemView_UpdateActivityItemTitle.text.isEmpty() or
                editText_ItemView_UpdateActivityDescription.text.isEmpty() or
                editText_ItemView_UpdateActivityStock.text.isEmpty() or
                editText_ItemView_UpdateActivityPrice.text.isEmpty())
            {

                Toast.makeText(this,"There is/are empty field(s)", Toast.LENGTH_SHORT).show()
                return

            }

            val tit=editText_ItemView_UpdateActivityItemTitle.text.toString()
            val des=editText_ItemView_UpdateActivityDescription.text.toString()
            val sto=editText_ItemView_UpdateActivityStock.text.toString().toInt()
            val pri=editText_ItemView_UpdateActivityPrice.text.toString().toFloat()
            val shi=spinner_ItemView_UpdateShipping.selectedItem.toString()
            val cat=spinner_ItemView_UpdateCategory.selectedItem.toString()


            AsyncUtil{
                val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(updateItemRequest(tit,des,sto,pri,shi,cat,uuid)))

                runOnUiThread{
                    Toast.makeText(this,responseList[1],Toast.LENGTH_SHORT).show()
                }
                if(responseList[0] == "true"){
                    changeEnable()
                    this.update_save =!this.update_save
                    finish()
                }

            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        }

    }

    fun delete(uuid: String){


        AsyncUtil{
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(deleteItemRequest(uuid)))

            runOnUiThread{
                Toast.makeText(this,responseList[1],Toast.LENGTH_SHORT).show()
            }
            if(responseList[0] == "true"){
                //which will activity open?
                finish()
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }


    fun deleteItemRequest(uu:String):String{
        return HttpUtil.sendPoststr("", "${getString(R.string.base_url)}/seller/my-items/"+uu+"/delete", SessionUtil.getSessionId(this)!!)
    }

    fun updateItemRequest(t:String,d:String,st:Int,p:Float,sh:String,c:String,uu:String):String{
        return HttpUtil.sendPoststr(UrlParamUtil.updateItemToUrlParam(t,p,d,sh,st,c), "${getString(R.string.base_url)}/seller/my-items/"+uu+"/update", SessionUtil.getSessionId(this)!!)
    }

}
