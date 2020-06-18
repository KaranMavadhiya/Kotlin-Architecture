package com.kotlin.architecture.registration.ui.login

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.registration.utils.ErrorCode
import com.kotlin.architecture.utils.CommonUtils

class LoginViewModel(application: Application) : BaseViewModel(application), Observable {

    private val context = getApplication<Application>().applicationContext

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()


    fun signIn() {
        when {
            inputEmail.value.isNullOrEmpty() -> {
                stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_EMAIL_VALIDATION,context.getString(R.string.err_please_enter_email_address))
            }
            !inputEmail.value?.let { CommonUtils.isValidEmailAddress(it) }!! -> {
                stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_EMAIL_VALIDATION,context.getString(R.string.err_please_enter_valid_email_address))
            }
            inputPassword.value.isNullOrEmpty() -> {
                stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_PASSWORD_VALIDATION,context.getString(R.string.err_please_enter_password))
            }
            else -> {
                val loginRequestModel = LoginRequestModel(inputEmail.value.toString(),inputPassword.value.toString())

                /*stateMutableLiveData.value = ViewState.InProgress
                APIManager.getRetrofitInstance(RegistrationInterceptor::class.java).callLoginApi(loginRequestModel).enqueue(object :
                    Callback<BaseResponseModel<UserModel>> {

                    override fun onFailure(call: Call<BaseResponseModel<UserModel>>, t: Throwable) {
                        stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_SERVER_ERROR, context.getString(R.string.str_error_message))
                    }

                    override fun onResponse(call: Call<BaseResponseModel<UserModel>>, response: Response<BaseResponseModel<UserModel>>) {
                        if (response.body() == null) {
                            stateMutableLiveData.value = ViewState.Failed(ErrorCode.STATUS_CODE_SERVER_ERROR, context.getString(R.string.str_error_message))
                        } else if (response.body()!!.status == ErrorCode.SUCCESS &&  response.body()!!.statusCode == ErrorCode.STATUS_CODE_SUCCESS) {
                            val responseModel =   response.body()!!.data
                            stateMutableLiveData.value = ViewState.Succeed(responseModel)
                        }else{
                            stateMutableLiveData.value = ViewState.Failed(  response.body()!!.statusCode,  response.body()!!.message)
                        }
                    }
                })*/

                stateMutableLiveData.value =  LoginRepository.getInstance().callLoginApi(context, loginRequestModel).value

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