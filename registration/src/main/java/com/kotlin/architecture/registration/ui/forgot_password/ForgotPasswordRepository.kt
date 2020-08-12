package com.kotlin.architecture.registration.ui.forgot_password

import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.APIManager
import com.kotlin.architecture.api.response.DataModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.api.RegistrationInterceptor
import com.kotlin.architecture.registration.api.request.ForgotPasswordRequestModel
import com.kotlin.architecture.utils.StatusCode
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ForgotPasswordRepository private constructor(private val stateMutableLiveData: MutableLiveData<BaseViewModel.ResultOf<BaseResponseModel<DataModel>>>) {

    fun callForgotPasswordApi(forgotPasswordRequestModel: ForgotPasswordRequestModel) {

        APIManager.getRetrofitInstance(RegistrationInterceptor::class.java).callForgotPasswordApi(forgotPasswordRequestModel).enqueue(object :  Callback<BaseResponseModel<DataModel>> {

            override fun onFailure(call: Call<BaseResponseModel<DataModel>>, t: Throwable) {
                stateMutableLiveData.postValue(BaseViewModel.ResultOf.Failure(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE, throwable = null))
            }

            override fun onResponse(call: Call<BaseResponseModel<DataModel>>, response: Response<BaseResponseModel<DataModel>>){
                if (response.body() == null) {
                    stateMutableLiveData.postValue(BaseViewModel.ResultOf.Failure(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE, throwable = null))
                } else if ( response.body()!!.status == StatusCode.SUCCESS &&  response.body()!! .statusCode == StatusCode.STATUS_CODE_SUCCESS) {
                    val responseModel =   response.body()!!
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
        private var instance: ForgotPasswordRepository? = null
        fun getInstance(stateMutableLiveData: MutableLiveData<BaseViewModel.ResultOf<BaseResponseModel<DataModel>>>) = instance ?: synchronized(this) {
                instance  ?: ForgotPasswordRepository(stateMutableLiveData).also { instance = it }
        }
    }
}