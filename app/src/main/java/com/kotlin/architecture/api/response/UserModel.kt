package com.kotlin.architecture.api.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserModel(

    @Json(name = "_id")
    var id: String,

    @Json(name = "name")
    var name: String,

    @Json(name = "emailId")
    var email: String,

    @Json(name = "access_token")
    var accessToken: String
)