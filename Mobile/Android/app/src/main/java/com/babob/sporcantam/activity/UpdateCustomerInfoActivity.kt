package com.babob.sporcantam.activity

import android.app.Activity
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Customer
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_update_customer_info.*
import java.nio.channels.AsynchronousByteChannel

class UpdateCustomerInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_customer_info)

        title = "Update Info"
        fillTheBoxes()

        button_updateCustomerUpdate.setOnClickListener { updateCustomer() }
    }

    fun fillTheBoxes(){
        AsyncUtil{
            val response = JsonUtil.customerInfoResponseToCustomer(HttpUtil.sendPoststr(
                    "",
                    "${getString(R.string.base_url)}/customer/", SessionUtil.getSessionId(this)!!))

            runOnUiThread {
                editText_updateCustomerFirstName.setText(response.firstName)
                editText_updateCustomerLastName.setText(response.lastName)
                editText_updateCustomerAddress.setText(response.address)
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun updateCustomer(){
        val customer = Customer ()
        customer.firstName = editText_updateCustomerFirstName.text.toString()
        customer.lastName = editText_updateCustomerLastName.text.toString()
        customer.address = editText_updateCustomerAddress.text.toString()

        AsyncUtil{
            val response = JsonUtil.generalServerResponseToList(
                    HttpUtil.sendPoststr(UrlParamUtil.customerToUrlParam(customer),
                    "${getString(R.string.base_url)}/customer/update", SessionUtil.getSessionId(this)!!))
            when {
                response.size < 2 -> runOnUiThread{ Toast.makeText(this, "Cannot connect to the server", Toast.LENGTH_SHORT).show() }
                response[0] == "true" -> runOnUiThread {
                    Toast.makeText(this, "Updated The Profile", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else -> runOnUiThread{ Toast.makeText(this, response[1], Toast.LENGTH_SHORT).show() }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}
