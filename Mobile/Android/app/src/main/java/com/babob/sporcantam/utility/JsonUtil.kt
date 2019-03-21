package com.babob.sporcantam.utility

import org.json.JSONObject
import java.util.*

class JsonUtil {

    companion object {

        fun signUpDataToJson(name:String, surname:String,companyName:String, email:String, password:String,userType:String ): JSONObject {
            return JSONObject(mapOf(
                    "first_name" to name,
                    "last_name" to surname,
                    "company_name" to companyName,
                    "email" to email,
                    "password" to password,
                    "UserType" to userType
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