package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.item.Customer
import com.babob.sporcantam.item.Seller
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_update_seller_info.*

class updateSellerInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_seller_info)

        title = "Update Info"
        fillTheBoxes()

        button_updateSellerUpdate.setOnClickListener { updateSeller() }
    }

    fun fillTheBoxes(){
        AsyncUtil{
            val response = JsonUtil.sellerInfoResponseToSeller(HttpUtil.sendPoststr(
                    "",
                    "${getString(R.string.base_url)}/seller/", SessionUtil.getSessionId(this)!!))

            runOnUiThread {
                editText_updateSellerFirstName.setText(response.firstName)
                editText_updateSellerLastName.setText(response.lastName)
                editText_updateSellerAddress.setText(response.address)
                editText_updateSellerIBAN.setText(response.IBAN)
                editText_updateSellerPhoneNumber.setText(response.phoneNumber)
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    fun updateSeller(){
        val seller = Seller ()
        seller.firstName = editText_updateSellerFirstName.text.toString()
        seller.lastName = editText_updateSellerLastName.text.toString()
        seller.address = editText_updateSellerAddress.text.toString()
        seller.IBAN = editText_updateSellerIBAN.text.toString()
        seller.phoneNumber = editText_updateSellerPhoneNumber.text.toString()

        AsyncUtil{
            val response = JsonUtil.generalServerResponseToList(
                    HttpUtil.sendPoststr(UrlParamUtil.sellertoUrlParam(seller),
                            "${getString(R.string.base_url)}/seller/update", SessionUtil.getSessionId(this)!!))
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
