package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.util.AsyncListUtil
import android.view.View
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.AsyncUtil
import com.babob.sporcantam.utility.HttpUtil
import com.babob.sporcantam.utility.UrlParamUtil
import kotlinx.android.synthetic.main.activity_seller_profile.*

class SellerProfileActivity : AppCompatActivity() {


    var isSending = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_profile)

        textView_sellerProfilePhoto.text = getString(R.string.seller_profile_activity_profile)
        textView_sellerProfileContactName.text =getString(R.string.seller_profile_contact_name)
        textView_sellerProfileCompanyName.text = getString(R.string.seller_profile_company_name)
        textView_sellerProfileEmail.text = getString(R.string.seller_profile_email)
        textView_sellerProfilePhone.text = getString(R.string.seller_profile_phone)
        textView_sellerProfileAddress.text = getString(R.string.seller_profile_address)

        title = getString(R.string.seller_profile_activity_title)

        button_sellerProfileSaveProfieBofile.setOnClickListener{ saveProfile() }





    }

    private fun saveProfile(){

        if(  editText_sellerProfileContactName.text.isEmpty() || editText_sellerProfileCompanyName.text.isEmpty() ||
                editText_sellerProfilrEmail.text.isEmpty() || editText_sellerProfilePhone.text.isEmpty() || editText_sellerProfileAddress.text.isEmpty())
        {
            Toast.makeText(this, getString(R.string.seller_profile_activity_invalid_save), Toast.LENGTH_SHORT).show()
            return
        }


        val contactName = editText_sellerProfileContactName.text.toString()
        val companyName = editText_sellerProfileCompanyName.text.toString()
        val email = editText_sellerProfilrEmail.text.toString()
        val phone = editText_sellerProfilePhone.text.toString()
        val address = editText_sellerProfileAddress.text.toString()


        isSending = true
        AsyncUtil{
            sendSellerProfileSaveRequest(contactName, companyName,email,phone,address)
            isSending = false
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }


    private fun sendSellerProfileSaveRequest(contactName:String, companyName: String, email: String, phone:String, address: String):Boolean{
        //TODO Integrate with API, use the below function in comments
        return true
    }

   /* private fun sendSellerProfileSaveRequest(contactName:String, companyName: String, email: String, phone:String, address: String):Boolean{
        if(loginType == 1){
            return HttpUtil.sendPost(UrlParamUtil.SellerSaveProfileDataToUrlParam(contactName,companyName,email, phone, address), "${getString(R.string.base_url)}, sessionId)
        }
        return HttpUtil.sendPost(UrlParamUtil.SellerSaveProfileDataToUrlParam(contactName,companyName,email, phone, address), "${getString(R.string.base_url)}, sessionId)
    }  */

}
