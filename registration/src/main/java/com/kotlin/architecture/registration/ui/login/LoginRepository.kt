package com.kotlin.architecture.registration.ui.login

import com.kotlin.architecture.api.APIManager
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.api.RegistrationInterceptor
import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.base.BaseViewModel.Companion.stateMutableLiveData
import com.kotlin.architecture.utils.ErrorCode
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository private constructor() {

    fun callLoginApi(loginRequestModel: LoginRequestModel){

        APIManager.getRetrofitInstance(RegistrationInterceptor::class.java).callLoginApi(loginRequestModel).enqueue(object : Callback<BaseResponseModel<UserModel>> {

            override fun onFailure(call: Call<BaseResponseModel<UserModel>>, t: Throwable) {
                stateMutableLiveData.value = BaseViewModel.ViewState.Validate(ErrorCode.STATUS_CODE_SERVER_ERROR, R.string.err_server_message)
            }

            override fun onResponse(call: Call<BaseResponseModel<UserModel>>, response: Response<BaseResponseModel<UserModel>>){
                if (response.body() == null) {
                    stateMutableLiveData.value = BaseViewModel.ViewState.Validate(ErrorCode.STATUS_CODE_SERVER_ERROR, R.string.err_server_message)
                } else if ( response.body()!!.status == ErrorCode.SUCCESS &&  response.body()!! .statusCode == ErrorCode.STATUS_CODE_SUCCESS) {
                    val responseModel =   response.body()!!.data
                    stateMutableLiveData.value = BaseViewModel.ViewState.Succeed(responseModel)
                }else{
                    stateMutableLiveData.value = BaseViewModel.ViewState.Failed(  response.body()!!.statusCode,  response.body()!!.message)
                }
            }
        })
    }

    companion object {
        @Volatile
        private var instance: LoginRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
                instance  ?: LoginRepository().also { instance = it }
        }
    }
}