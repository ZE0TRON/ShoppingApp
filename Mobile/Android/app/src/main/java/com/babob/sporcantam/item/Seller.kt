package com.babob.sporcantam.item

import java.io.Serializable

class Seller:Serializable {
    var firstName:String = ""
    var lastName:String = ""
    var address:String = ""
    var IBAN:String = ""
    var phoneNumber:String = ""
    var email:String=""

    constructor(firstName:String, lastName:String, address:String, IBAN:String, phoneNumber:String, email:String){
        this.firstName=firstName
        this.lastName=lastName
        this.address=address
        this.IBAN=IBAN
        this.phoneNumber=phoneNumber
        this.email=email
    }

    constructor()
}