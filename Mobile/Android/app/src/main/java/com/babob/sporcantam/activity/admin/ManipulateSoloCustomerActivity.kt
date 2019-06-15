package com.babob.sporcantam.activity.admin

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Customer
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_manipulate_solo_customer.*

class ManipulateSoloCustomerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manipulate_solo_customer)

        val customer:Customer = intent.getSerializableExtra("customer") as Customer

        title="Manipulate Customer"

        fillFields(customer)

        button_ManipulateSoloCustomer_delete.setOnClickListener { delete() }
        button_ManipulateSoloCustomer_save.setOnClickListener { save() }

    }

    fun fillFields(cu:Customer){

        editText_ManipulateSoloCustomer_firstname.setText(cu.firstName)
        editText_ManipulateSoloCustomer_lastname.setText(cu.lastName)
        editText_ManipulateSoloCustomer_address.setText(cu.address)
        editText_ManipulateSoloCustomer_email.setText(cu.email)

    }

    fun save(){
        val customer= Customer()
        customer.firstName = editText_ManipulateSoloCustomer_firstname.text.toString()
        customer.lastName = editText_ManipulateSoloCustomer_lastname.text.toString()
        customer.address = editText_ManipulateSoloCustomer_address.text.toString()
        customer.email = editText_ManipulateSoloCustomer_email.toString()

        AsyncUtil{
            val responseList = CheckerUtil.responseListChecker(JsonUtil.generalServerResponseToList(saveRequest(customer)))

            runOnUiThread{
                Toast.makeText(this,responseList[1], Toast.LENGTH_SHORT).show()
            }
            if(responseList[0] == "true"){
                //which will activity open?
                finish()
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    fun saveRequest(cu:Customer):String{
        return HttpUtil.sendPoststr(UrlParamUtil.customerToUrlParam(cu),"${R.string.base_url}/admin/customer/update", SessionUtil.getSessionId(this)!!)
    }

    fun delete(){
        AsyncUtil{
            val mail:String = editText_ManipulateSoloCustomer_email.text.toString()
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
        return HttpUtil.sendPoststr(em,"${R.string.base_url}/admin/customer/delete", SessionUtil.getSessionId(this)!!)
    }
}
