package com.kotlin.architecture.registration.api

import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.registration.api.response.UserModel
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface RegistrationInterceptor {

    /**
     * login api
     *
     * {
     *    "deviceToken": "<DEVICE TOKEN>",
     *    "deviceType": "<PLATFORM>",
     *    "emailId": "<EMAIL ADDRESS>",
     *    "password": "<PASSWORD>"
     * }
     */
    @POST("v1/auth/login")
    fun callLoginApi(@Body requestBody: LoginRequestModel): Call<BaseResponseModel<UserModel>>

}