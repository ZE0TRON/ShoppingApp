package com.babob.sporcantam.utility

import android.content.Context
import android.os.SystemClock
import android.util.Log
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class HttpUtil {
    companion object {

        /**
         * Call it from AsyncTask or else the app will EXPLODE!
         */
        fun sendPost(jsonParam: JSONObject, Url: String, sId:String): Boolean {
            Log.d("HTTP_UTIL", "Sending Post Request to: $Url")
            try {
                val url = URL(Url)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                //conn.setRequestProperty("Accept", "*/*")
                conn.setRequestProperty("Cookie", sId)

                conn.doOutput = true
                conn.doInput = true
                conn.connectTimeout = 4000

                val os = DataOutputStream(conn.outputStream)
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                Log.i("JSON", "Sending JsonFile (POST)")
                Log.i("JSON", jsonParam.toString())
                os.writeBytes(jsonParam.toString())
                os.flush()
                os.close()
                Log.i("STATUS", conn.responseCode.toString())
                Log.i("MSG", conn.responseMessage)

                conn.disconnect()

            } catch (e: Exception) {
                Log.i("SENDERR", e.toString())
                return false
            }

            return true
        }


    }
}