package com.babob.sporcantam.item

import java.io.Serializable

class Customer:Serializable {
    var firstName:String = ""
    var lastName:String = ""
    var address:String = ""
    var email:String=""

    constructor( firstName:String, lastName:String, address:String = "",email:String){
        this.firstName=firstName
        this.lastName=lastName
        this.address=address
        this.email=email
    }
    constructor()
}