package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    var isSending = false
    var loginType = 1
    var TAG = "LOGIN_ACTIVITY"
    lateinit var sessionId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = getString(R.string.login_activity_title)

        getSId()
        checkSignedUP()
        button_login.setOnClickListener { loginUser() }
        button_switch_login_type.setOnClickListener{ changeLoginType() }
        button_switchToSIgnUp.setOnClickListener { ActivityOpenerUtil.openSignUpActivity(this) }
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

    fun loginUser(){
        if(isSending){
            return
        }
        if(editText_LoginEmail.text.isEmpty() || editText_LoginPassword.text.isEmpty()){
            Toast.makeText(this, getString(R.string.login_activity_toast_empty_boxes), Toast.LENGTH_SHORT).show()
            return
        }
        val email = editText_LoginEmail.text.toString()
        val password = editText_LoginPassword.text.toString()
        if(! CheckerUtil.emailChecker(email)){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_email), Toast.LENGTH_SHORT).show()
            return
        }
        if(! CheckerUtil.checkPassword(password)){
            Toast.makeText(this, getString(R.string.sign_up_activity_toast_invalid_password), Toast.LENGTH_SHORT).show()
            return
        }

        isSending = true
        AsyncUtil{
            if(sendLoginRequest(email, password)){
                ActivityOpenerUtil.openSellerItemListActivity(this)
                isSending = false
                runOnUiThread { finish() }
            }
            else{
                isSending = false
                runOnUiThread {
                    Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    private fun changeLoginType(){
        if(loginType == 1){
            loginType = 2
            textView_login_info.text = getString(R.string.sign_up_activity_info_seller)
            button_switch_login_type.text = getString(R.string.sign_up_activity_info_customer)
        }
        else{
            loginType = 1
            textView_login_info.text = getString(R.string.sign_up_activity_info_customer)
            button_switch_login_type.text = getString(R.string.sign_up_activity_info_seller)

        }
    }

    private fun checkSignedUP(){
        if(SignUpActivity.isSignedUp){
            editText_LoginEmail.text = SignUpActivity.givenEmail
        }
    }

    private fun sendLoginRequest(email: String, password:String):Boolean{
        val response: String
        if(loginType == 1){
            response = HttpUtil.sendPoststr(UrlParamUtil.loginDataToUrlParam(email, password), "${getString(R.string.base_url)}/customer/login", sessionId)
        }
        else{
            response = HttpUtil.sendPoststr(UrlParamUtil.loginDataToUrlParam(email, password), "${getString(R.string.base_url)}/seller/login", sessionId)
        }
        Log.d(TAG, response + JsonUtil.HandleStringResponse(response))
        return JsonUtil.HandleStringResponse(response)

    }
}
