package com.ricki.umbevents.Model

import java.io.Serializable

class ParticipantModel : Serializable {
    var id : String = ""
    var name : String = ""
    var phone : String = ""
    var email : String = ""
    var gender : String = ""
    var image : String = ""
    var idType : String = ""
    var username : String = ""
    var password : String = ""

    constructor(
        id: String,
        name: String,
        phone: String,
        email: String,
        gender: String,
        image: String,
        idType: String,
        username: String,
        password: String
    ) {
        this.id = id
        this.name = name
        this.phone = phone
        this.email = email
        this.gender = gender
        this.image = image
        this.idType = idType
        this.username = username
        this.password = password
    }
}