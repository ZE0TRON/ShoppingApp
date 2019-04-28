package com.babob.sporcantam.utility

import java.util.Date

class UrlParamUtil {

    companion object {

        fun signUpDataToUrlParam(name:String, surname:String,companyName:String, email:String, password:String,userType:String ): String {
            return "email=$email" +
                    "&password=$password" +
                    "&first_name=$name" +
                    "&last_name=$surname" +
                    "&company_name=$companyName" +
                    "&userType=$userType"
        }

        fun loginDataToUrlParam(email: String, password: String):String{
            return "email=$email" +
                    "&password=$password"
        }

        fun createItemToUrlParam(item_title: String, price: Float, description: String, shipping_info: String, stock_count: Int, uuid: String):String{
            return "item_title=$item_title" +
                    "&price=$price"+
                    "&item_description=$description"+
                    "&shipping_info=$shipping_info"+
                    "&stock_count=$stock_count"+
                    "&UUID=$uuid"
        }


        fun updateItemToUrlParam(item_title: String, price: Float, description: String, shipping_info: String, stock_count: Int):String{
            return "item_title=$item_title" +
                    "&price=$price"+
                    "&item_description=$description"+
                    "&shipping_info=$shipping_info"+
                    "&stock_count=$stock_count"
        }

    }
}