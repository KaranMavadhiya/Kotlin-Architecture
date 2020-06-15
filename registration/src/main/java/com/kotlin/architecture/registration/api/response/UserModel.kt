package com.kotlin.architecture.registration.api.response

import com.squareup.moshi.Json

data class UserModel(

    @Json(name = "_id")
    var _id: String,

    @Json(name = "name")
    var name: String,

    @Json(name = "emailId")
    var emailId: String,

    @Json(name = "access_token")
    var access_token: String
)