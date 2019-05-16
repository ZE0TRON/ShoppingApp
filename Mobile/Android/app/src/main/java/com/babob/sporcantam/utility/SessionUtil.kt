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
    }
}