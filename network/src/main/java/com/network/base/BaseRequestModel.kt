package com.network.base

import com.squareup.moshi.Json

data class BaseRequestModel<T>(
    @Json(name = "deviceType")
    var deviceType: String,

    @Json(name = "deviceToken")
    var deviceToken: String,

    @Json(name = "data")
    var data: T
)