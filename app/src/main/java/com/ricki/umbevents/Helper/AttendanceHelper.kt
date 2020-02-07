package com.ricki.umbevents.Helper

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.ricki.eventapps.Helper.FuelJson
import com.ricki.umbevents.Entities.GlobalVariable
import com.ricki.umbevents.Model.AttendanceModel
import java.net.URLEncoder

class AttendanceHelper {

    fun getStatusCheckIn(idevent : String, idparticipant: String) : Boolean{
        try{
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceGetStatusCheckin
            url += "?idEvent=" + idevent + "&idParticipant=" + idparticipant
            fuelJson = FuelJson(
                fuelNoRedirect.request(Method.GET, url)
                    .responseJson()
            )

            fuelJson.Result.fold(
                {
                    var result = it.content.toBoolean()
                    return result
                },
                {
                    throw Exception(it.toString())
                }
            )
        }catch (ex : Exception){
            return false
        }
    }

    fun getStatusCertificate(idevent : String, idparticipant: String) : Boolean{
        try{
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceGetStatusCertificate
            url += "?idEvent=" + idevent + "&idParticipant=" + idparticipant
            fuelJson = FuelJson(
                fuelNoRedirect.request(Method.GET, url)
                    .responseJson()
            )

            fuelJson.Result.fold(
                {
                    var result = it.content.toBoolean()
                    return result
                },
                {
                    throw Exception(it.toString())
                }
            )
        }catch (ex : Exception){
            return false
        }
    }

    fun checkInAttendance(idparticipant: String, idEvent: String) : Boolean{
        val fuelJson: FuelJson
        val fuelNoRedirect = FuelManager()
        fuelNoRedirect.removeAllResponseInterceptors()

        var url = GlobalVariable().webServiceCheckin
        var content = "IDParticipant=" + URLEncoder.encode(idparticipant, "UTF-8")
        content += "&IDEvent=" + URLEncoder.encode(idEvent, "UTF-8")
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

    fun checkOutAttendance(idparticipant: String, idEvent: String) : Boolean{
        val fuelJson: FuelJson
        val fuelNoRedirect = FuelManager()
        fuelNoRedirect.removeAllResponseInterceptors()

        var url = GlobalVariable().webServiceCheckout
        var content = "IDParticipant=" + URLEncoder.encode(idparticipant, "UTF-8")
        content += "&IDEvent=" + URLEncoder.encode(idEvent, "UTF-8")
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
}