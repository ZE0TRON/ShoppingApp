package com.babob.sporcantam.item

import java.io.Serializable




class Order: Serializable {

    var seller_email:String
    var customer_email:String
    var order_id: Int


    constructor(seller_email: String, customer_email:String, order_id: Int) {
        this.seller_email = seller_email
        this.customer_email = customer_email
        this.order_id = order_id
    }




}