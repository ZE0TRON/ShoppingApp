package com.babob.sporcantam.activity

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil
import java.util.*

class MainActivity : AppCompatActivity() {

    var TAG = "Main Activity"
    //TODO: Fix the sessionId Logic: Batuhan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityOpenerUtil.openLoginActivity(this)
        finish()

    }

}
