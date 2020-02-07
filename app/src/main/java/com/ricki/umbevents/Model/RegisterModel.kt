package com.ricki.umbevents.Model

class RegisterModel {
    var id: String = ""
    var name: String = ""
    var email: String = ""
    var phone: String = ""
    var gender: String = ""
    var image: String = ""
    var idType: String = ""
    var username: String = ""
    var password: String = ""

    constructor(
        id: String,
        name: String,
        email: String,
        phone: String,
        gender: String,
        image: String,
        idType: String,
        username: String,
        password: String
    ) {
        this.id = id
        this.name = name
        this.email = email
        this.phone = phone
        this.gender = gender
        this.image = image
        this.idType = idType
        this.username = username
        this.password = password
    }
}