package com.babob.sporcantam.item

import java.time.LocalDate
import java.util.*
import java.util.UUID.randomUUID



class Item {

    private lateinit var item_title:String
    private var price:Float=0.0f
    private lateinit var seller:String
    private lateinit var description:String
    private lateinit var shipping_info:String
    private  var stock_count:Int = 0
    private lateinit var publish_daate:LocalDate
    private lateinit var uuid:String

    constructor(item_title: String, price: Float, seller: String, description: String, shipping_info: String, stock_count: Int, publish_date: LocalDate, uuid: String) {
        this.item_title = item_title
        this.price = price
        this.seller = seller
        this.description = description
        this.shipping_info = shipping_info
        this.stock_count = stock_count
        this.publish_daate = publish_date
        this.uuid = uuid
    }




}