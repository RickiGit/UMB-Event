package com.ricki.umbevents.Helper

import android.content.Context
import android.content.SharedPreferences

class SettingsHelper(context: Context) {
    private val PREFS_FILENAME = "com.ricki.umbevent.prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    val editPrefs = prefs.edit()

    /*Authentication*/
    private val _id = "id_key"
    private val _name = "name_key"
    private val _email = "email_key"
    private val _phone = "phone_key"
    private val _gender = "gender_key"
    private val _image = "image_key"
    private val _idType = "idType_key"
    private val _username = "username_key"
    private val _password = "password_key"

    var id: String
        get() = prefs.getString(_id, "")
        set(value) = editPrefs.putString(_id, value).apply()

    var name: String
        get() = prefs.getString(_name, "")
        set(value) = editPrefs.putString(_name, value).apply()

    var email: String
        get() = prefs.getString(_email, "")
        set(value) = editPrefs.putString(_email, value).apply()

    var phone: String
        get() = prefs.getString(_phone, "")
        set(value) = editPrefs.putString(_phone, value).apply()

    var gender: String
        get() = prefs.getString(_gender, "")
        set(value) = editPrefs.putString(_gender, value).apply()

    var image: String
        get() = prefs.getString(_image, "")
        set(value) = editPrefs.putString(_image, value).apply()

    var idType: String
        get() = prefs.getString(_idType, "")
        set(value) = editPrefs.putString(_idType, value).apply()

    var username: String
        get() = prefs.getString(_username, "")
        set(value) = editPrefs.putString(_username, value).apply()

    var password: String
        get() = prefs.getString(_password, "")
        set(value) = editPrefs.putString(_password, value).apply()
}