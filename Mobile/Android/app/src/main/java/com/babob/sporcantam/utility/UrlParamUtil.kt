package com.babob.sporcantam.utility

import com.babob.sporcantam.item.Customer
import com.babob.sporcantam.item.Item
import com.babob.sporcantam.item.Seller

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

        fun createItemToUrlParam(item_title: String, price: Float, description: String, shipping_info: String, stock_count: Int, item_category: String,uuid: String):String{
            return "item_title=$item_title" +
                    "&price=$price"+
                    "&item_description=$description"+
                    "&shipping_info=$shipping_info"+
                    "&stock_count=$stock_count"+
                    "&category=$item_category"+
                    "&UUID=$uuid"
        }


        fun updateItemToUrlParam(item_title: String, price: Float, description: String, shipping_info: String, stock_count: Int,category: String):String{
            return "item_title=$item_title" +
                    "&price=$price"+
                    "&item_description=$description"+
                    "&shipping_info=$shipping_info"+
                    "&stock_count=$stock_count"+
                    "&category=$category"
        }

        fun itemUUIDParam(item:Item):String{
            return "itemID=${item.uuid}"
        }

        fun customerToUrlParam(customer: Customer):String{
            return "first_name=${customer.firstName}" +
                    "&last_name=${customer.lastName}"+
                    "&address=${customer.address}"+
                    "&email=${customer.email}"
        }

        fun sellertoUrlParam(seller: Seller):String{
            return "first_name=${seller.firstName}" +
                    "&last_name=${seller.lastName}"+
                    "&company_address=${seller.address}"+
                    "&IBAN=${seller.IBAN}"+
                    "&phone_number=${seller.phoneNumber}"+
                    "&email=${seller.email}"
        }

        fun paymentToUrlParam(card:String,cvc:String,expire:String, balance:Double  ):String{
            return "cardNumber=$card" +
                    "&cvc=$cvc"+
                    "&expireDate=$expire"+
                    "&balance=$balance"
        }

        fun searchItem(item_title: String):String{
            return "item_title=$item_title"
        }

        fun categorySearchItem(cat:String):String{
            return "category=$cat"
        }

        fun UUIDtoUrlParam(uuid:String):String{
            return "UUID=$uuid"
        }

        fun itemtoUrlParam(t:String,d:String,st:Int,p:Float,sh:String,c:String,uu:String):String{
            return "item_title=$t"+
                    "&description=$d"+
                    "&stock_count=$st"+
                    "&price=$p"+
                    "&shipping_info=$sh"+
                    "&category=$c"+
                    "&UUID=$uu"
        }

        fun emailToUrlParam(email:String):String{
            return "email=$email"
        }

        fun saleIdToUrlParam(id:String):String{
            return "saleID=$id"
        }

    }
}