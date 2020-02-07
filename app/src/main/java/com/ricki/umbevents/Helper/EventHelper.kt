package com.ricki.umbevents.Helper

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.ricki.eventapps.Helper.FuelJson
import com.ricki.umbevents.Entities.GlobalVariable
import com.ricki.umbevents.Model.EventModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EventHelper {
    var dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale("id","ID"))

    fun getAllEvent() : ArrayList<EventModel>?{
        try{
            var listOfEvents : ArrayList<EventModel> = ArrayList()
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceGetAllEvent
            fuelJson = FuelJson(
                fuelNoRedirect.request(Method.GET, url)
                    .responseJson()
            )

            fuelJson.Result.fold(
                {
                    var eventArray = it.array()

                    for(i in 0 until eventArray.length()){
                        var item = eventArray.getJSONObject(i)
                        var id = item.getString("ID")
                        var title = item.getString("Title")
                        var description = item.getString("Description")
                        var location = item.getString("Location")
                        var latittude = item.getString("Latitude")
                        var longitude = item.getString("Longitude")
                        var start = item.getString("StartDate")
                        var end = item.getString("EndDate")
                        var normalPrice = item.getDouble("NormalPrice")
                        var highPrice = item.getDouble("HighPrice")
                        var otherPrice = item.getDouble("OtherPrice")
                        var idType = item.getString("Type")
                        var quota = item.getInt("Quota")
                        var startDate = dateFormat.parse(start)
                        var endDate = dateFormat.parse(end)

                        listOfEvents.add(EventModel(id, title, description, location, latittude, longitude, startDate, endDate, normalPrice, highPrice, otherPrice, quota, idType))
                    }
                    return listOfEvents
                },
                {
                    throw Exception(it.toString())
                }
            )
        }catch (ex : Exception){
            return null
        }
    }

    fun getAllMyEvent(idParticipant : String) : ArrayList<EventModel>?{
        try{
            var listOfEvents : ArrayList<EventModel> = ArrayList()
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceGetAllMyEvent + "?idParticipant=" + idParticipant
            fuelJson = FuelJson(
                fuelNoRedirect.request(Method.GET, url)
                    .responseJson()
            )

            fuelJson.Result.fold(
            {
                var eventArray = it.array()

                for(i in 0 until eventArray.length()){
                    var item = eventArray.getJSONObject(i)
                    var id = item.getString("ID")
                    var title = item.getString("Title")
                    var description = item.getString("Description")
                    var location = item.getString("Location")
                    var latittude = item.getString("Latitude")
                    var longitude = item.getString("Longitude")
                    var start = item.getString("StartDate")
                    var end = item.getString("EndDate")
                    var normalPrice = item.getDouble("NormalPrice")
                    var highPrice = item.getDouble("HighPrice")
                    var otherPrice = item.getDouble("OtherPrice")
                    var quota = item.getInt("Quota")
                    var idType = item.getString("Type")

                    var startDate = dateFormat.parse(start)
                    var endDate = dateFormat.parse(end)

                    listOfEvents.add(EventModel(id, title, description, location, latittude, longitude, startDate, endDate, normalPrice, highPrice, otherPrice, quota, idType))
                }
                return listOfEvents
            },
            {
                throw Exception(it.toString())
            })
        }catch (ex : Exception) {
            return null
        }
    }

    fun getQuotaAvailable(idEvent: String) : Int{
        try{
            var quotaAvailable : Int = 0
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceGetQuotaAvailable + "?idEvent=" + idEvent
            fuelJson = FuelJson(
                fuelNoRedirect.request(Method.GET, url)
                    .responseJson()
            )

            fuelJson.Result.fold(
                {
                    quotaAvailable = it.content.toInt()
                    return quotaAvailable
                },
                {
                    throw Exception(it.toString())
                }
            )
        }catch (ex : Exception){
            return 0
        }
    }
}