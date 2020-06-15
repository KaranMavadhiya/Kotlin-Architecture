package com.kotlin.architecture.registration.api.response
import com.squareup.moshi.Json

data class DialCode(
    @Json(name = "countryCode")
    val countryCode: String,

    @Json(name = "countryName")
    val countryName: String,

    @Json(name = "countryDialCode")
    val countryDialCode: String
)