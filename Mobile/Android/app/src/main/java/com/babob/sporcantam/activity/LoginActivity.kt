package com.babob.sporcantam.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    var url = "http://www.example.com"
    var isSending = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = getString(R.string.login_activity_title)

        checkSignedUP()
        button_login.setOnClickListener { loginUser() }
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
            sendLoginRequest(email, password)
            isSending = false
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)

    }

    private fun checkSignedUP(){
        if(SignUpActivity.isSignedUp){
            editText_LoginEmail.text = SignUpActivity.givenEmail
        }
    }

    private fun sendLoginRequest(email: String, password:String):Boolean{
        return HttpUtil.sendPost(JsonUtil.loginDataToJson(email, password), url)
    }
}
