package com.babob.sporcantam.activity

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.babob.sporcantam.R
import com.babob.sporcantam.utility.ActivityOpenerUtil
import com.babob.sporcantam.utility.SessionUtil
import java.util.*

class MainActivity : AppCompatActivity() {

    var TAG = "Main Activity"
    //TODO: Fix the sessionId Logic: Batuhan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(SessionUtil.isLogged(this)){
            val userType = SessionUtil.getUserType(this)
            ActivityOpenerUtil.openMainPageActivity(this, userType)
        } else {
            ActivityOpenerUtil.openMainPageActivity(this, 1)
        }
        finish()

    }

}
