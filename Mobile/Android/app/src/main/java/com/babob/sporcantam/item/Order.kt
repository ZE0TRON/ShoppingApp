package com.babob.sporcantam.item

import java.io.Serializable




class Order: Serializable {

    var isConfirmed:Int
    var customer_email:String
    var order_id: String


    constructor(isConfirmed:Int, customer_email:String, order_id: String) {
        this.isConfirmed = isConfirmed
        this.customer_email = customer_email
        this.order_id = order_id
    }




}