package com.kotlin.architecture.registration.api.request

import com.kotlin.architecture.utils.CommonUtils
import com.kotlin.architecture.utils.Constants
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForgotPasswordRequestModel(
    @Json(name = "emailId")
    var email: String
) {
    @Json(name = "deviceToken")
    var deviceToken: String = CommonUtils.uniqueId

    @Json(name = "deviceType")
    var deviceType: String = Constants.PLATFORM
}