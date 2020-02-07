package com.ricki.umbevents.Model

import java.util.*

class RegistrationEventModel {
    var idParticipant : String = ""
    var idEvent : String = ""
    var price : Double = 0.0

    constructor(idParticipant: String, idEvent: String, price: Double) {
        this.idParticipant = idParticipant
        this.idEvent = idEvent
        this.price = price
    }
}

class DetailRegistrationModel{
    var id : String = ""
    var idParticipant : String = ""
    var name : String = ""
    var nameEvent : String = ""
    var registerDate : Date? = null
    var price : Double = 0.0
    var status : Int = 0

    constructor(
        id: String,
        idParticipant: String,
        name: String,
        nameEvent: String,
        registerDate: Date?,
        price: Double,
        status: Int
    ) {
        this.id = id
        this.idParticipant = idParticipant
        this.name = name
        this.nameEvent = nameEvent
        this.registerDate = registerDate
        this.price = price
        this.status = status
    }
}