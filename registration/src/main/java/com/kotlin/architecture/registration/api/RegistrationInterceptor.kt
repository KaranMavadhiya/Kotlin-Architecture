package com.kotlin.architecture.registration.api

import com.kotlin.architecture.api.response.DataModel
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.registration.api.request.ForgotPasswordRequestModel
import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.registration.api.request.SignupRequestModel
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationInterceptor {

    @POST("v1/auth/signup")
    fun callSignupApi(@Body requestBody: SignupRequestModel): Call<BaseResponseModel<DataModel>>

    @POST("v1/auth/login")
    fun callLoginApi(@Body requestBody: LoginRequestModel): Call<BaseResponseModel<UserModel>>

    @POST("v1/auth/forgotPassword")
    fun callForgotPasswordApi(@Body requestBody: ForgotPasswordRequestModel): Call<BaseResponseModel<DataModel>>

}