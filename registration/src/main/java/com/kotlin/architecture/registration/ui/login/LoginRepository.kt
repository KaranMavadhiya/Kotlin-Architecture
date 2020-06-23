package com.kotlin.architecture.registration.ui.login

import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.APIManager
import com.kotlin.architecture.registration.api.RegistrationInterceptor
import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.base.BaseViewModel

import com.kotlin.architecture.utils.StatusCode
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository private constructor(private val stateMutableLiveData: MutableLiveData<BaseViewModel.ViewState>) {

    fun callLoginApi(loginRequestModel: LoginRequestModel){
        APIManager.getRetrofitInstance(RegistrationInterceptor::class.java).callLoginApi(loginRequestModel).enqueue(object : Callback<BaseResponseModel<UserModel>> {

            override fun onFailure(call: Call<BaseResponseModel<UserModel>>, t: Throwable) {
                stateMutableLiveData.value = BaseViewModel.ViewState.Failed(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE)
            }

            override fun onResponse(call: Call<BaseResponseModel<UserModel>>, response: Response<BaseResponseModel<UserModel>>){
                if (response.body() == null) {
                    stateMutableLiveData.value = BaseViewModel.ViewState.Failed(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE)
                } else if ( response.body()!!.status == StatusCode.SUCCESS &&  response.body()!! .statusCode == StatusCode.STATUS_CODE_SUCCESS) {
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
        fun getInstance(stateMutableLiveData: MutableLiveData<BaseViewModel.ViewState>) = instance ?: synchronized(this) {
                instance  ?: LoginRepository(stateMutableLiveData).also { instance = it }
        }
    }
}