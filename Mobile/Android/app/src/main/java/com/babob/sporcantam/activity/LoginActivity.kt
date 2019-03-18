package com.babob.sporcantam.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.babob.sporcantam.R
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

    }

    fun checkSignedUP(){
        if(SignUpActivity.isSignedUp){
            editText_LoginEmail.text = SignUpActivity.givenEmail
        }
    }
}
