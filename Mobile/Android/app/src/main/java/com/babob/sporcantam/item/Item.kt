package com.babob.sporcantam.item

import java.io.Serializable




class Item: Serializable {

    var item_title:String
    var price:Float=0.00f
    var seller:String
    var description:String
    var shipping_info:String
    var stock_count:Int = 0
    var uuid:String


    constructor(item_title: String, price: Float, seller: String, description: String, shipping_info: String, stock_count: Int, uuid: String) {
        this.item_title = item_title
        this.price = price
        this.seller = seller
        this.description = description
        this.shipping_info = shipping_info
        this.stock_count = stock_count
        this.uuid = uuid
    }






}