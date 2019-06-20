package com.babob.sporcantam.activity.admin

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Seller
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_manipulate_solo_seller.*

class ManipulateSoloSellerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipulate_solo_seller)

        val seller:Seller = intent.getSerializableExtra("seller") as Seller

        fillFields(seller)

        button_AdminSoloSeller_Save.setOnClickListener { save() }

        button_AdminSoloSeller_Delete.setOnClickListener {  delete() }

    }

    fun fillFields(se:Seller){

        editText_AdminSoloSeller_firstname.setText(se.firstName)
        editText_AdminSoloSeller_secondName.setText(se.lastName)
        editText_AdminSoloSeller_Address.setText(se.address)
        editText_AdminSoloSeller_iban.setText(se.IBAN)
        editText_AdminSoloSeller_phone.setText(se.phoneNumber)
        editText_AdminSoloSeller_email.setText(se.email)

    }

    fun save(){
        val seller = Seller ()
        seller.firstName = editText_AdminSoloSeller_firstname.text.toString()
        seller.lastName = editText_AdminSoloSeller_secondName.text.toString()
        seller.address = editText_AdminSoloSeller_Address.text.toString()
        seller.IBAN = editText_AdminSoloSeller_iban.text.toString()
        seller.phoneNumber = editText_AdminSoloSeller_phone.text.toString()
        seller.email = editText_AdminSoloSeller_email.text.toString()

        AsyncUtil{
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(saveRequest(seller)))

            runOnUiThread{
                Toast.makeText(this,responseList[1], Toast.LENGTH_SHORT).show()
            }
            if(responseList[0] == "true"){
                //which will activity open?
                finish()
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    fun saveRequest(se:Seller):String{
        return HttpUtil.sendPoststr(UrlParamUtil.sellertoUrlParam(se),"${getString(R.string.base_url)}/admin/seller/update",SessionUtil.getSessionId(this)!!)
    }

    fun delete(){
        AsyncUtil{
            val mail:String = editText_AdminSoloSeller_email.text.toString()
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(deleteRequest(mail)))

            runOnUiThread{
                Toast.makeText(this,responseList[1], Toast.LENGTH_SHORT).show()
            }
            if(responseList[0] == "true"){
                //which will activity open?
                finish()
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun deleteRequest(em:String):String{
        return HttpUtil.sendPoststr(em,"${getString(R.string.base_url)}/admin/seller/delete",SessionUtil.getSessionId(this)!!)
    }
}
