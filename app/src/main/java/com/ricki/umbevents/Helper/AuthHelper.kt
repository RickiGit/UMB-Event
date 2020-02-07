package com.ricki.umbevents.Helper

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.ricki.eventapps.Helper.FuelJson
import com.ricki.umbevents.Entities.GlobalVariable
import com.ricki.umbevents.Model.ParticipantModel
import com.ricki.umbevents.Model.RegisterModel
import org.json.JSONObject
import java.net.URLEncoder

class AuthHelper {
    fun checkUserLogin(username:String, password:String) : ParticipantModel {
        var participantModel : ParticipantModel? = null
        val fuelJson: FuelJson
        val fuelNoRedirect = FuelManager()
        fuelNoRedirect.removeAllResponseInterceptors()

        var url = GlobalVariable().webServiceCheckLogin
        var content = "username=" + URLEncoder.encode(username, "UTF-8")
        content += "&password=" + URLEncoder.encode(password, "UTF-8")
        fuelJson = FuelJson(
            fuelNoRedirect.request(Method.POST, url)
                .header("Content-Type" to "application/x-www-form-urlencoded")
                .body(content)
                .responseJson()
        )

        fuelJson.Result.fold(
            {
                var itemUser : JSONObject
                if (!it.content.equals("null")) {
                    itemUser = it.obj()
                    if(itemUser != null){
                        var id = itemUser.getString("ID")
                        var name = itemUser.getString("Name")
                        var email = itemUser.getString("Email")
                        var phone = itemUser.getString("Phone")
                        var gender = itemUser.getString("Gender")
                        var image = itemUser.getString("Image")
                        var idType = itemUser.getString("IDType")
                        var username = itemUser.getString("Username")
                        var password = itemUser.getString("Password")

                        participantModel = ParticipantModel(id, name, email, phone, gender, image, idType, username, password)
                        return participantModel!!
                    }
                }

                return null!!
            },
            {
                throw Exception(it.toString())
            }
        )
    }

    fun register(registerModel: RegisterModel) : String{
        val fuelJson: FuelJson
        val fuelNoRedirect = FuelManager()
        fuelNoRedirect.removeAllResponseInterceptors()

        var url = GlobalVariable().webServiceRegister
        var content = "ID=" + URLEncoder.encode(registerModel.id, "UTF-8")
        content += "&Name=" + URLEncoder.encode(registerModel.name, "UTF-8")
        content += "&Email=" + URLEncoder.encode(registerModel.email, "UTF-8")
        content += "&Phone=" + URLEncoder.encode(registerModel.phone, "UTF-8")
        content += "&Gender=" + URLEncoder.encode(registerModel.gender, "UTF-8")
        content += "&Image=" + URLEncoder.encode(registerModel.image, "UTF-8")
        content += "&IDType=" + URLEncoder.encode(registerModel.idType, "UTF-8")
        content += "&Username=" + URLEncoder.encode(registerModel.username, "UTF-8")
        content += "&Password=" + URLEncoder.encode(registerModel.password, "UTF-8")
        fuelJson = FuelJson(
            fuelNoRedirect.request(Method.POST, url)
                .header("Content-Type" to "application/x-www-form-urlencoded")
                .body(content)
                .responseJson()
        )

        fuelJson.Result.fold(
            {
                var result = it.content.toString()
                result = result.replace("\"","")
                return result
            },
            {
                throw Exception(it.toString())
            }
        )
    }
}