package com.kotlin.architecture.registration.api.request

import com.kotlin.architecture.utils.Constants
import com.squareup.moshi.Json

data class LoginRequestModel(
    @Json(name = "emailId")
    var emailId: String,

    @Json(name = "password")
    var password: String,

    @Json(name = "deviceType")
    var deviceType: String = Constants.PLATFORM,

    @Json(name = "deviceToken")
    var deviceToken: String
)