package com.kotlin.architecture.registration.ui.login

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.response.UserModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.api.request.LoginRequestModel
import com.kotlin.architecture.utils.StatusCode
import com.kotlin.architecture.utils.CommonUtils
import com.kotlin.architecture.utils.CommonUtils.isNetworkAvailable

class LoginViewModel(application: Application) : BaseViewModel(application), Observable {

    private val context = getApplication<Application>().applicationContext

    private val _userModel = MutableLiveData<ResultOf<UserModel>>()
    val userModel: LiveData<ResultOf<UserModel>> = _userModel

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()

    fun signIn() {
        when {
            inputEmail.value.isNullOrEmpty() -> {
                _userModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_EMAIL_VALIDATION, context.getString(R.string.err_please_enter_email_address)))
            }
            !inputEmail.value?.let { CommonUtils.isValidEmailAddress(it) }!! -> {
                _userModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_EMAIL_VALIDATION, context.getString(R.string.err_please_enter_valid_email_address)))
            }
            inputPassword.value.isNullOrEmpty() -> {
                _userModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_PASSWORD_VALIDATION, context.getString(R.string.err_please_enter_password)))
            }
            !context.isNetworkAvailable() -> {
                _userModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_INTERNET_VALIDATION, context.getString(R.string.err_please_check_your_internet_connection)))
            }
            else -> {
                _userModel.postValue(ResultOf.InProgress)
                val loginRequestModel = LoginRequestModel(inputEmail.value.toString(),inputPassword.value.toString())
                LoginRepository.getInstance(_userModel).callLoginApi(loginRequestModel)
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