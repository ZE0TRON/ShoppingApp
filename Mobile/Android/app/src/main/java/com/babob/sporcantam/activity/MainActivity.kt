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

        val sessionId = getSessionIdFromStorage()
        if(sessionId == null){
            ActivityOpenerUtil.openSignUpActivity(this)
            createAndSaveSessionId()
            finish()
        }
        ActivityOpenerUtil.openSignUpActivity(this)


    }


    private fun getSessionIdFromStorage():String?{
        val prefs = getSharedPreferences("storage", Context.MODE_PRIVATE)
        return prefs.getString("sId", null)
    }

    private fun createAndSaveSessionId(){
        val uuid = UUID.randomUUID().toString()
        val editor = getSharedPreferences("storage", Context.MODE_PRIVATE).edit()
        editor.putString("sId", uuid)
        editor.apply()
        Log.i(TAG,"created new UUID: $uuid")
    }
}
