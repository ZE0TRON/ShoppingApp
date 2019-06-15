package com.babob.sporcantam.item

class Seller {
    var firstName:String = ""
    var lastName:String = ""
    var address:String = ""
    var IBAN:String = ""
    var phoneNumber:String = ""

    constructor(firstName:String, lastName:String, address:String, IBAN:String, phoneNumber:String){
        this.firstName=firstName
        this.lastName=lastName
        this.address=address
        this.IBAN=IBAN
        this.phoneNumber=phoneNumber
    }

    constructor()
}