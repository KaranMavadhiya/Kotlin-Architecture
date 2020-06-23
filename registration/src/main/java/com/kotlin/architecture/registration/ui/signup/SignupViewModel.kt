package com.kotlin.architecture.registration.ui.signup

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.api.request.SignupRequestModel
import com.kotlin.architecture.utils.CommonUtils
import com.kotlin.architecture.utils.CommonUtils.isNetworkAvailable
import com.kotlin.architecture.utils.StatusCode

class SignupViewModel(application: Application) : BaseViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val stateMutableLiveData = MutableLiveData<ViewState>(ViewState.Idle)
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    val signupInfo = SignupInfo()

    fun signup() {
        when {
            signupInfo.inputFullName.value.isNullOrEmpty() -> {
                stateMutableLiveData.value = ViewState.Validate(StatusCode.STATUS_CODE_NAME_VALIDATION, R.string.err_please_enter_full_name)
            }
            signupInfo.inputEmail.value.isNullOrEmpty() -> {
                stateMutableLiveData.value = ViewState.Validate(StatusCode.STATUS_CODE_EMAIL_VALIDATION, R.string.err_please_enter_email_address)
            }
            !signupInfo.inputEmail.value?.let { CommonUtils.isValidEmailAddress(it) }!! -> {
                stateMutableLiveData.value =
                    ViewState.Validate(StatusCode.STATUS_CODE_EMAIL_VALIDATION, R.string.err_please_enter_valid_email_address)
            }
            signupInfo.inputPassword.value.isNullOrEmpty() -> {
                stateMutableLiveData.value = ViewState.Validate(StatusCode.STATUS_CODE_PASSWORD_VALIDATION, R.string.err_please_enter_password)
            }
            !CommonUtils.isValidPassword(signupInfo.inputPassword.value.toString()) -> {
                stateMutableLiveData.value = ViewState.Validate(StatusCode.STATUS_CODE_PASSWORD_VALIDATION, R.string.err_please_enter_valid_password)
            }
            !context.isNetworkAvailable() -> {
                stateMutableLiveData.value =
                    ViewState.Validate(StatusCode.STATUS_CODE_INTERNET_VALIDATION, R.string.err_please_check_your_internet_connection)
            }
            else -> {
                stateMutableLiveData.value = ViewState.InProgress
                val signupRequestModel = SignupRequestModel(
                    signupInfo.inputFullName.value.toString(),
                    signupInfo.inputEmail.value.toString(),
                    signupInfo.inputPassword.value.toString()
                )
                SignupRepository.getInstance(stateMutableLiveData).callSignupApi(signupRequestModel)
            }
        }
    }
}