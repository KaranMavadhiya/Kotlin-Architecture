package com.kotlin.architecture.registration.ui.login

import android.app.Application
import android.text.TextUtils
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.APIManager
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.api.RegistrationInterceptor
import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.registration.api.response.UserModel
import com.kotlin.architecture.registration.utils.ErrorCode
import com.kotlin.architecture.utils.CommonUtils
import com.kotlin.architecture.utils.Constants
import com.network.base.BaseResponseModel
import retrofit2.Call
import retrofit2.Response

class LoginViewModel(application: Application) : BaseViewModel(application), Observable {

    private val context = getApplication<Application>().applicationContext

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    fun signIn() {
        when {
            TextUtils.isEmpty(inputEmail.value) -> {
                stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_EMAIL_VALIDATION,context.getString(R.string.err_please_enter_email_address))
            }
            !inputEmail.value?.let { CommonUtils.isValidEmailAddress(it) }!! -> {
                stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_EMAIL_VALIDATION,context.getString(R.string.err_please_enter_valid_email_address))
            }
            TextUtils.isEmpty(inputPassword.value) -> {
                stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_PASSWORD_VALIDATION,context.getString(R.string.err_please_enter_password))
            }
            else -> {
                stateMutableLiveData.value = ViewState.InProgress

                val loginRequestModel = LoginRequestModel(inputEmail.value.toString(),inputPassword.value.toString(), Constants.PLATFORM, uniqueId)

                    APIManager.getRetrofitInstance(RegistrationInterceptor::class.java).callLoginApi(loginRequestModel).enqueue(object :
                        retrofit2.Callback<BaseResponseModel<UserModel>> {

                        override fun onFailure(call: Call<BaseResponseModel<UserModel>>, t: Throwable) {
                            stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_SERVER_ERROR,context.getString(R.string.str_error_message))
                        }

                        override fun onResponse(call: Call<BaseResponseModel<UserModel>>, response: Response<BaseResponseModel<UserModel>>) {

                            if (response.body() != null && response.body()!!.status == ErrorCode.SUCCESS && response.body()!!.statusCode == ErrorCode.STATUS_CODE_SUCCESS) {
                                val responseModel =  response.body()!!.data
                                responseModel?.let { ViewState.Succeed(it) }
                            }else{
                                stateMutableLiveData.value = ViewState.Failed(response.body()!!.statusCode, response.body()!!.message)
                            }
                        }
                    })
            }
        }
    }

    fun facebook() {

    }

    fun google() {

    }

    fun twitter() {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}