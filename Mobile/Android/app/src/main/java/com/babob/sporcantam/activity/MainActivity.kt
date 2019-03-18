package com.babob.sporcantam.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityOpenerUtil.openSignUpActivity(this)
        finish()
    }
}
