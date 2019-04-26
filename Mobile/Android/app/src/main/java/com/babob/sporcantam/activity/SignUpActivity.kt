package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.babob.sporcantam.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.support.constraint.ConstraintSet
import com.babob.sporcantam.utility.*


class SignUpActivity : AppCompatActivity() {

    lateinit var sessionId:String
    var isSending = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        title = getString(R.string.sign_up_activity_title)

        getSId()
        initSpinner()
        button_signUpButton.setOnClickListener { signUp() }
        button_signUpSwichLogin.setOnClickListener {ActivityOpenerUtil.openLoginActivity(this)}
        spinner_signUpCustomerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateFrontend()
            }

        }
    }

    private fun getSId(){
        val id = SessionUtil.getSessionId(this)
        if(id == null){
            ActivityOpenerUtil.openMainActivity(this)
            finish()
        }
        else{
            sessionId = id
        }
    }

    fun initSpinner(){
        val spinnerArray = ArrayList<String>()
        spinnerArray.add("Customer")
        spinnerArray.add("Seller")

        val adapter = ArrayAdapter(
                this, android.R.layout.simple_spinner_item, spinnerArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val sItems = spinner_signUpCustomerType
        sItems.adapter = adapter

        spinner_signUpCustomerType.setSelection(0)
    }

    fun updateFrontend(){
        if(spinner_signUpCustomerType.selectedItemPosition == 1){
            //seller
            editText_signUpCompanyName.visibility = View.VISIBLE
        }
        else{
            //customer
            editText_signUpCompanyName.visibility = View.GONE

        }
    }

    private fun signUp(){
        if(isSending){
            return
        }

        if( editText_signUpName.text.isEmpty() || editText_signUpSurname.text.isEmpty()){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_name), Toast.LENGTH_SHORT).show()
            return
        }

        var companyName = ""
        //seller
        if(editText_signUpCompanyName.visibility == View.VISIBLE){
            if(editText_signUpCompanyName.text.isEmpty()){
                Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_company), Toast.LENGTH_SHORT).show()
                return
            }
            companyName = editText_signUpCompanyName.text.toString()
        }
        val name = editText_signUpName.text.toString()
        val surname = editText_signUpSurname.text.toString()
        var email = ""
        if(!editText_signUpEmail.text.isEmpty()){
            email = editText_signUpEmail.text.toString()

        }
        var password = ""
        if(!editText_signUpPassword.text.isEmpty()){
            password = editText_signUpPassword.text.toString()

        }

        if(! CheckerUtil.emailChecker(email)){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_email), Toast.LENGTH_SHORT).show()
            return
        }
        if(! CheckerUtil.checkPassword(password)){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_password), Toast.LENGTH_SHORT).show()
            return
        }

        var userType = "Seller"
        if(companyName == ""){
            userType = "Customer"
        }

        isSending = true
        AsyncUtil{
            if(sendDataToApi(name,surname,companyName,email,password,userType)){
                givenEmail = editText_signUpEmail.text
                isSignedUp = true
                ActivityOpenerUtil.openLoginActivity(this)
                finish()
            }
            else{
                runOnUiThread {
                    Toast.makeText(this, "Cannot connect to the server. Please try again later", Toast.LENGTH_SHORT).show()
                }
            }
            isSending = false
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    private fun sendDataToApi(n:String, s:String, cn:String, eml:String, psw:String, typ:String):Boolean{
        return HttpUtil.sendPost(UrlParamUtil.signUpDataToUrlParam(n,s,cn,eml,psw,typ), "${getString(R.string.base_url)}/user/add", sessionId)
    }




    companion object {
        var isSignedUp = false
        lateinit var givenEmail: Editable
    }

}
