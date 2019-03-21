package com.babob.sporcantam.utility

import android.content.Context

class SessionUtil {
    companion object {
        private var sessionId: String? = null

        fun getSessionId(context: Context):String?{
            if(sessionId == null){
                getSessionId(context)
            }
            return sessionId
        }

        private fun getSessionIdfromStorage(context: Context){
            val prefs = context.getSharedPreferences("storage", Context.MODE_PRIVATE)
            sessionId =  prefs.getString("sId", null)
        }
    }
}