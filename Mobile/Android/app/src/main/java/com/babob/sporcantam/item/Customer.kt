package com.babob.sporcantam.item

class Customer {
    var firstName:String = ""
    var lastName:String = ""
    var address:String = ""

    constructor( firstName:String, lastName:String, address:String = ""){
        this.firstName=firstName
        this.lastName=lastName
        this.address=address
    }
    constructor()
}