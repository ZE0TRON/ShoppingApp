package com.babob.sporcantam.utility

import android.util.Log
import com.babob.sporcantam.item.Customer
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.item.Seller
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonUtil {
    companion object {
        fun handleStringResponse(response: String): Boolean {
            try {
                val jsonObj = JSONObject(response)
                return jsonObj.get("success") as Boolean
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return false
        }

        fun generalServerResponseToList(response: String):List<String>{
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

        fun getItemResponseToList(response: String):ArrayList<Item>{
            try {
                val jsonObj = JSONObject(response)
                val retList = arrayListOf<Item>()
                val arr = jsonObj.getJSONArray("items")
                var i = 0
                while (i < arr.length()){
                    val itemJsonObj = arr.getJSONObject(i++)
                    val itemTitle = itemJsonObj.get("item_title") as String
                    val description = itemJsonObj.get("description") as String
                    val price = itemJsonObj.get("price") as Double
                    val seller = itemJsonObj.get("seller") as String
                    val shipping = itemJsonObj.get("shipping_info") as String
                    val stockCount = itemJsonObj.get("stock_count") as Int
                    val uuid = itemJsonObj.get("uuid") as String
                    val item = Item(itemTitle, price.toFloat(), seller, description, shipping, stockCount, uuid)
                    retList.add(item)
                }
                return retList

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return arrayListOf()
        }

        fun customerInfoResponseToCustomer(response: String):Customer{
            try{
                val jsonObj = JSONObject(response)
                val customer = Customer()
                customer.firstName = jsonObj.getString("first_name")
                customer.lastName = jsonObj.getString("last_name")
                customer.address = jsonObj.getString("address")
                return customer
            }catch (e: JSONException) {
                e.printStackTrace()
            }
            return Customer()
        }

        fun sellerInfoResponseToSeller(response: String): Seller {
            try{
                val jsonObj = JSONObject(response)
                val seller = Seller()
                seller.firstName = jsonObj.getString("first_name")
                seller.lastName = jsonObj.getString("last_name")
                seller.address = jsonObj.getString("company_address")
                seller.IBAN = jsonObj.getString("iban")
                seller.phoneNumber = jsonObj.getString("phone_number")

                return seller
            }catch (e: JSONException) {
                e.printStackTrace()
            }
            return Seller()
        }


    }
}