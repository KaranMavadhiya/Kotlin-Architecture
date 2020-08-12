package com.kotlin.architecture.registration.ui.signup

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.response.DataModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.api.request.SignupRequestModel
import com.kotlin.architecture.utils.CommonUtils
import com.kotlin.architecture.utils.CommonUtils.isNetworkAvailable
import com.kotlin.architecture.utils.StatusCode
import com.network.base.BaseResponseModel

class SignupViewModel(application: Application) : BaseViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _signupModel = MutableLiveData<ResultOf<BaseResponseModel<DataModel>>>()
    val signupModel: LiveData<ResultOf<BaseResponseModel<DataModel>>> = _signupModel

    val signupInfo = SignupInfo()

    fun signup() {
        when {
            signupInfo.inputFullName.value.isNullOrEmpty() -> {
                _signupModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_NAME_VALIDATION, context.getString(R.string.err_please_enter_full_name)))
            }
            signupInfo.inputEmail.value.isNullOrEmpty() -> {
                _signupModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_EMAIL_VALIDATION, context.getString(R.string.err_please_enter_email_address)))
            }
            ! signupInfo.inputEmail.value?.let { CommonUtils.isValidEmailAddress(it) }!! -> {
                _signupModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_EMAIL_VALIDATION, context.getString(R.string.err_please_enter_valid_email_address)))
            }
            signupInfo.inputPassword.value.isNullOrEmpty() -> {
                _signupModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_PASSWORD_VALIDATION, context.getString(R.string.err_please_enter_password)))
            }
            !CommonUtils.isValidPassword(signupInfo.inputPassword.value.toString()) -> {
                _signupModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_PASSWORD_VALIDATION, context.getString(R.string.err_please_enter_valid_password)))
            }
            !context.isNetworkAvailable() -> {
                _signupModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_INTERNET_VALIDATION, context.getString(R.string.err_please_check_your_internet_connection)))
            }
            else -> {
                _signupModel.postValue(ResultOf.InProgress)
                val signupRequestModel = SignupRequestModel(signupInfo.inputFullName.value.toString(), signupInfo.inputEmail.value.toString(), signupInfo.inputPassword.value.toString())
                SignupRepository.getInstance(_signupModel).callSignupApi(signupRequestModel)
            }
        }
    }
}