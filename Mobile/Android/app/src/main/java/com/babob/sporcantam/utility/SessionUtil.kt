package com.babob.sporcantam.utility

import android.content.Context
import android.util.Log
import java.util.*

class SessionUtil {
    companion object {
        private var sessionId: String? = null
        private val TAG = "SESSION UTIL"

        fun getSessionId(context: Context):String?{
            if(sessionId == null){
                getSessionIdfromStorage(context)
            }
            if(sessionId == null){
                createSessionId(context)
            }
            Log.d("ses", sessionId)
            return sessionId
        }

        private fun getSessionIdfromStorage(context: Context){
            val prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE)
            sessionId =  prefs.getString("sId", null)
        }

        fun createSessionId(context: Context):String?{
            val uuid = UUID.randomUUID().toString()
            sessionId = uuid
            saveSessionIdToStorage(context)
            Log.i(TAG,"created new UUID: $uuid")
            return sessionId
        }

        private fun saveSessionIdToStorage(context: Context){
            val editor = context.getSharedPreferences("storage", Context.MODE_PRIVATE).edit()
            editor.putString("sId", sessionId)
            editor.apply()
        }

        fun login(context: Context){
            val editor = context.getSharedPreferences("storage", Context.MODE_PRIVATE).edit()
            editor.putBoolean("login", true)
            editor.apply()
        }

        fun logOut(context: Context){
            val editor = context.getSharedPreferences("storage", Context.MODE_PRIVATE).edit()
            editor.putBoolean("login", false)
            editor.apply()
        }

        fun isLogged(context: Context):Boolean{
            val prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE)
            return prefs.getBoolean("login", false)
        }

        fun saveUserType(context: Context, userType:Int){
            val editor = context.getSharedPreferences("storage", Context.MODE_PRIVATE).edit()
            editor.putInt("userType", userType)
            editor.apply()
        }

        fun getUserType(context: Context):Int{
            val prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE)
            return prefs.getInt("userType", 1)
        }
    }
}