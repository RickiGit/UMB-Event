package com.ricki.umbevents.Model

import java.io.Serializable
import java.util.*

class EventModel : Serializable {
    var id : String = ""
    var title : String = ""
    var description : String = ""
    var location : String = ""
    var latitude : String = ""
    var longitude : String = ""
    var startDate : Date? = null
    var endDate : Date? = null
    var normalPrice : Double = 0.0
    var highPrice : Double = 0.0
    var otherPrice : Double = 0.0
    var quota : Int = 0
    var idType : String = ""

    constructor(id: String, title: String) {
        this.id = id
        this.title = title
    }

    constructor(
        id: String,
        title: String,
        description: String,
        location: String,
        latitude: String,
        longitude: String,
        startDate: Date?,
        endDate: Date?,
        normalPrice: Double,
        highPrice: Double,
        otherPrice: Double,
        quota: Int,
        idType: String
    ) {
        this.id = id
        this.title = title
        this.description = description
        this.location = location
        this.latitude = latitude
        this.longitude = longitude
        this.startDate = startDate
        this.endDate = endDate
        this.normalPrice = normalPrice
        this.highPrice = highPrice
        this.otherPrice = otherPrice
        this.quota = quota
        this.idType = idType
    }


}