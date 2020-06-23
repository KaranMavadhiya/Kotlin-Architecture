package com.kotlin.architecture.registration.ui.signup

import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.APIManager
import com.kotlin.architecture.api.response.DataModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.api.RegistrationInterceptor
import com.kotlin.architecture.registration.api.request.SignupRequestModel
import com.kotlin.architecture.utils.StatusCode
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupRepository private constructor(private val stateMutableLiveData: MutableLiveData<BaseViewModel.ViewState>) {

    fun callSignupApi(signupRequestModel: SignupRequestModel) {

        APIManager.getRetrofitInstance(RegistrationInterceptor::class.java).callSignupApi(signupRequestModel)
            .enqueue(object : Callback<BaseResponseModel<DataModel>> {

                override fun onFailure(call: Call<BaseResponseModel<DataModel>>, t: Throwable) {
                    stateMutableLiveData.value = BaseViewModel.ViewState.Failed(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE)
                }

                override fun onResponse(call: Call<BaseResponseModel<DataModel>>, response: Response<BaseResponseModel<DataModel>>) {
                    if (response.body() == null) {
                        stateMutableLiveData.value =
                            BaseViewModel.ViewState.Failed(StatusCode.STATUS_CODE_SERVER_ERROR, StatusCode.SERVER_ERROR_MESSAGE)
                    } else if (response.body()!!.status == StatusCode.SUCCESS && response.body()!!.statusCode == StatusCode.STATUS_CODE_SUCCESS) {
                        val responseModel = response.body()
                        stateMutableLiveData.value = BaseViewModel.ViewState.Succeed(responseModel)
                    } else {
                        stateMutableLiveData.value = BaseViewModel.ViewState.Failed(response.body()!!.statusCode, response.body()!!.message)
                    }
                }
            })
    }

    companion object {
        @Volatile
        private var instance: SignupRepository? = null
        fun getInstance(stateMutableLiveData: MutableLiveData<BaseViewModel.ViewState>) = instance ?: synchronized(this) {
            instance ?: SignupRepository(stateMutableLiveData).also { instance = it }
        }
    }
}