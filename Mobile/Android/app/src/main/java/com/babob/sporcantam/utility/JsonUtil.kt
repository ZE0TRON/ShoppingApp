package com.babob.sporcantam.utility

import org.json.JSONObject
import java.util.*

class JsonUtil {

    companion object {

        fun signUpDataToJson(name:String, surname:String,companyName:String, email:String, password:String,userType:String ): JSONObject {
            return JSONObject(mapOf(
                    "name" to name,
                    "surname" to surname,
                    "company" to companyName,
                    "email" to email,
                    "password" to password,
                    "type" to userType
            ))
        }

        fun loginDataToJson(email: String, password: String):JSONObject{
            return JSONObject(mapOf(
                    "email" to email,
                    "password" to password
            ))
        }
    }
}