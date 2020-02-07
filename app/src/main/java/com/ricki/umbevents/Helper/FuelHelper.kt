package com.ricki.eventapps.Helper

import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result

class FuelData constructor(data: Triple<Request, Response, Result<Any, FuelError>>) {
    val Request = data.first
    val Response = data.second
    val Result = data.third
}

class FuelDataByteArray(data: Triple<Request, Response, Result<ByteArray, FuelError>>) {
    val Request = data.first
    val Response = data.second
    val Result = data.third
}

class FuelJson(data: Triple<Request, Response, Result<Json, FuelError>>) {
    val Request = data.first
    val Response = data.second
    val Result = data.third
}