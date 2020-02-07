package com.ricki.umbevents.Helper

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.ricki.eventapps.Helper.FuelJson
import com.ricki.umbevents.Entities.GlobalVariable
import com.ricki.umbevents.Model.DetailRegistrationModel
import com.ricki.umbevents.Model.RegistrationEventModel
import java.lang.Exception
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

class RegistrationEventHelper {

    var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale("id","ID"))

    fun registerEvent(registrationEvent : RegistrationEventModel) : Boolean {
        val fuelJson: FuelJson
        val fuelNoRedirect = FuelManager()
        fuelNoRedirect.removeAllResponseInterceptors()

        var url = GlobalVariable().webServiceRegistrationEvent
        var content = "IDParticipant=" + URLEncoder.encode(registrationEvent.idParticipant, "UTF-8")
        content += "&IDEvent=" + URLEncoder.encode(registrationEvent.idEvent, "UTF-8")
        content += "&Price=" + URLEncoder.encode(registrationEvent.price.toString(), "UTF-8")
        fuelJson = FuelJson(
            fuelNoRedirect.request(Method.POST, url)
                .header("Content-Type" to "application/x-www-form-urlencoded")
                .body(content)
                .responseJson()
        )

        fuelJson.Result.fold(
            {
                return it.content.toBoolean()
            },
            {
                throw Exception(it.toString())
            }
        )
    }

    fun getStatusRegistration(idParticipant : String, idEvent: String) : Boolean{
        try{
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceCheckStatusRegistration + "?idParticipant=" + idParticipant + "&idEvent=" + idEvent
            fuelJson = FuelJson(
                fuelNoRedirect.request(Method.GET, url)
                    .responseJson()
            )

            fuelJson.Result.fold(
                {
                    return it.content.toBoolean()
                },
                {
                    throw Exception(it.toString())
                }
            )
        }catch (ex : Exception){
            return false
        }
    }

    fun getDetailRegistration(idParticipant : String, idEvent: String) : DetailRegistrationModel?{
        try{
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceCheckDetailRegistration + "?idParticipant=" + idParticipant + "&idEvent=" + idEvent
            fuelJson = FuelJson(
                fuelNoRedirect.request(Method.GET, url)
                    .responseJson()
            )

            fuelJson.Result.fold(
                {
                    var item = it.obj()
                    if(item != null){
                        var id = item.getString("ID")
                        var idParticipant = item.getString("IDParticipant")
                        var name = item.getString("Name")
                        var eventName = item.getString("NameEvent")
                        var price = item.getDouble("Price")
                        var rgDate = item.getString("RegisterDate")
                        var status = item.getInt("Status")
                        var registerDate = dateFormat.parse(rgDate)

                        var result = DetailRegistrationModel(id, idParticipant, name, eventName, registerDate, price, status)
                        return result
                    }

                    return null
                },
                {
                    throw Exception(it.toString())
                }
            )
        }catch (ex : Exception){
            return null
        }
    }
}