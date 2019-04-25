package com.babob.sporcantam.utility

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class JsonUtil {
    companion object {
        fun HandleStringResponse(response: String): Boolean {
            try {
                val jsonObj = JSONObject(response)
                return jsonObj.get("success") as Boolean

            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return false
        }
    }
}