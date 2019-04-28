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

        fun AddItemResponseToStringList(response: String):List<String>{
            try {
                val jsonObj = JSONObject(response)
                val res1 = jsonObj.get("success") as Boolean
                val res2 = jsonObj.get("msg") as String
                return listOf(res1.toString(),res2)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return listOf()
        }

        fun UpdateItemResponseToStringList(response: String):List<String>{
            try {
                val jsonObj = JSONObject(response)
                val res1 = jsonObj.get("success") as Boolean
                val res2 = jsonObj.get("msg") as String
                return listOf(res1.toString(),res2)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return listOf()
        }

        fun deleteItemResponseToStringList(response: String):List<String>{
            try {
                val jsonObj = JSONObject(response)
                val res1 = jsonObj.get("success") as Boolean
                val res2 = jsonObj.get("msg") as String
                return listOf(res1.toString(),res2)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return listOf()
        }

    }
}