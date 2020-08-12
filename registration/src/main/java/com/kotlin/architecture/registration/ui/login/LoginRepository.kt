package com.kotlin.architecture.registration.ui.login

import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.APIManager
import com.kotlin.architecture.registration.api.RegistrationInterceptor
import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R

import com.kotlin.architecture.utils.StatusCode
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository private constructor(private val stateMutableLiveData: MutableLiveData<BaseViewModel.ResultOf<UserModel>>) {

    fun callLoginApi(loginRequestModel: LoginRequestModel){
        APIManager.getRetrofitInstance(RegistrationInterceptor::class.java).callLoginApi(loginRequestModel).enqueue(object : Callback<BaseResponseModel<UserModel>> {

            override fun onFailure(call: Call<BaseResponseModel<UserModel>>, t: Throwable) {
                stateMutableLiveData.postValue(BaseViewModel.ResultOf.Failure(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE, t))
            }

            override fun onResponse(call: Call<BaseResponseModel<UserModel>>, response: Response<BaseResponseModel<UserModel>>){
                if (response.body() == null) {
                    stateMutableLiveData.postValue(BaseViewModel.ResultOf.Failure(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE, throwable = null))
                } else if ( response.body()!!.status == StatusCode.SUCCESS &&  response.body()!! .statusCode == StatusCode.STATUS_CODE_SUCCESS) {
                    val responseModel =   response.body()!!.data!!
                    stateMutableLiveData.postValue(BaseViewModel.ResultOf.Success(responseModel))
                }else{
                    val message = response.body()!!.message
                    stateMutableLiveData.postValue(BaseViewModel.ResultOf.Failure(response.body()!!.statusCode, message, throwable = null))
                }
            }
        })
    }

    companion object {
        @Volatile
        private var instance: LoginRepository? = null
        fun getInstance(stateMutableLiveData: MutableLiveData<BaseViewModel.ResultOf<UserModel>>) = instance ?: synchronized(this) {
                instance  ?: LoginRepository(stateMutableLiveData).also { instance = it }
        }
    }
}