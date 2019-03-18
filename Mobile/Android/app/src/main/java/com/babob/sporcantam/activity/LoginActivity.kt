package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil
import com.babob.sporcantam.utility.CheckerUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = getString(R.string.login_activity_title)

        checkSignedUP()
        button_login.setOnClickListener { loginUser() }
    }

    fun loginUser(){
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

        //TODO: Implement api connection

    }

    fun checkSignedUP(){
        if(SignUpActivity.isSignedUp){
            editText_LoginEmail.text = SignUpActivity.givenEmail
        }
    }
}
