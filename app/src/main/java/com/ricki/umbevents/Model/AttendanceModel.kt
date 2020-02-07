package com.ricki.umbevents.Model

import java.io.Serializable
import java.util.*

class AttendanceModel : Serializable {
    var id : String = ""
    var idParticipant: String = ""
    var idEvent: String = ""
    var checkInDate: Date? = null
    var checkOutDate: Date? = null
    var status: String = ""

    constructor(
        id: String,
        idParticipant: String,
        idEvent: String,
        checkInDate: Date?,
        checkOutDate: Date?,
        status: String
    ) {
        this.id = id
        this.idParticipant = idParticipant
        this.idEvent = idEvent
        this.checkInDate = checkInDate
        this.checkOutDate = checkOutDate
        this.status = status
    }
}