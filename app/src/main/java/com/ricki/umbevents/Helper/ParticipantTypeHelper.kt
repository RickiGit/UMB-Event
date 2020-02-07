package com.ricki.umbevents.Helper

import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.ricki.eventapps.Helper.FuelJson
import com.ricki.umbevents.Entities.GlobalVariable
import com.ricki.umbevents.Model.ParticipantTypeModel

class ParticipantTypeHelper {

    fun getAllParticipantType() : ArrayList<ParticipantTypeModel>?{
        try{
            var listOfParticipantType : ArrayList<ParticipantTypeModel> = ArrayList()
            val fuelJson: FuelJson
            val fuelNoRedirect = FuelManager()
            fuelNoRedirect.removeAllResponseInterceptors()

            var url = GlobalVariable().webServiceGetAllParticipantType
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
                        var name = item.getString("Name")
                        var description = item.getString("Description")

                        listOfParticipantType.add(ParticipantTypeModel(id, name, description))
                    }
                    return listOfParticipantType
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