package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_item_view_update.*

class ItemView_UpdateActivity : AppCompatActivity() {

    var update_save = true
    /*
    true-> view page
    false-> update page
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_view_update)


        val item: Item = intent.getSerializableExtra("item") as Item

        fillFields(item)

        button_ItemView_UpdateActivityUpdateSave.setOnClickListener { helper(item.uuid) }
        button_ItemView_UpdateActivityDelete.setOnClickListener {
            AsyncUtil{ delete(item.uuid) }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
             }



    }
    fun fillFields(item: Item){
        editText_ItemView_UpdateActivityItemTitle.setText(item.item_title)
        editText_ItemView_UpdateActivityDescription.setText(item.description)
        editText_ItemView_UpdateActivityStock.setText(item.stock_count.toString())
        editText_ItemView_UpdateActivityPrice.setText(item.price.toString())
        editText_ItemView_UpdateActivityShipping.setText(item.shipping_info)
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
        editText_ItemView_UpdateActivityShipping.isEnabled=!editText_ItemView_UpdateActivityShipping.isEnabled


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
                editText_ItemView_UpdateActivityPrice.text.isEmpty() or
                editText_ItemView_UpdateActivityShipping.text.isEmpty() )
            {

                Toast.makeText(this,"There is/are empty field(s)", Toast.LENGTH_SHORT).show()
                return

            }

            val tit=editText_ItemView_UpdateActivityItemTitle.text.toString()
            val des=editText_ItemView_UpdateActivityDescription.text.toString()
            val sto=editText_ItemView_UpdateActivityStock.text.toString().toInt()
            val pri=editText_ItemView_UpdateActivityPrice.text.toString().toFloat()
            val shi=editText_ItemView_UpdateActivityShipping.text.toString()

            //TODO: Duplicate, fix = okan
            AsyncUtil{
                val responseList = JsonUtil.generalServerResponseToList(updateItemRequest(tit,des,sto,pri,shi,uuid))
                if(responseList.isEmpty()){
                    runOnUiThread {
                        Toast.makeText(this, "cannot connect to the server", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    runOnUiThread {
                        Toast.makeText(this, responseList[1], Toast.LENGTH_SHORT).show()
                        if(responseList[0] == "true"){
                            changeEnable()
                            this.update_save =!this.update_save
                            finish()
                        }
                    }
                }

            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

        }

    }

    fun delete(uuid: String){
        //TODO: Duplicate, fix = okan
        val responseList = JsonUtil.generalServerResponseToList(deleteItemRequest(uuid))
        if(responseList.isEmpty()){
            runOnUiThread {
                Toast.makeText(this, "cannot connect to the server", Toast.LENGTH_SHORT).show()
            }
            return
        }
        runOnUiThread {
            Toast.makeText(this, responseList[1], Toast.LENGTH_SHORT).show()
            if(responseList[0] == "true"){
                //TODO
                //which will activity open?
                finish()
            }
        }

    }


    fun deleteItemRequest(uu:String):String{
        return HttpUtil.sendPoststr("", "${getString(R.string.base_url)}/seller/my-items/"+uu+"/delete", SessionUtil.getSessionId(this)!!)
    }

    fun updateItemRequest(t:String,d:String,st:Int,p:Float,sh:String,uu:String):String{
        return HttpUtil.sendPoststr(UrlParamUtil.updateItemToUrlParam(t,p,d,sh,st), "${getString(R.string.base_url)}/seller/my-items/"+uu+"/update", SessionUtil.getSessionId(this)!!)
    }

}
