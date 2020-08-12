package com.kotlin.architecture.registration.ui.forgot_password

import android.app.Application
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.api.response.DataModel
import com.kotlin.architecture.base.BaseViewModel
import com.kotlin.architecture.registration.R
import com.kotlin.architecture.registration.api.request.ForgotPasswordRequestModel
import com.kotlin.architecture.utils.CommonUtils
import com.kotlin.architecture.utils.CommonUtils.isNetworkAvailable
import com.kotlin.architecture.utils.StatusCode
import com.network.base.BaseResponseModel

class ForgotPasswordViewModel(application: Application) : BaseViewModel(application), Observable {

    private val context = getApplication<Application>().applicationContext

    private val _forgotPasswordModel = MutableLiveData<ResultOf<BaseResponseModel<DataModel>>>()
    val forgotPasswordModel: LiveData<ResultOf<BaseResponseModel<DataModel>>> = _forgotPasswordModel

    @Bindable
    val inputEmail = MutableLiveData<String>()

    fun submit() {
        when {

            inputEmail.value.isNullOrEmpty() -> {
                _forgotPasswordModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_EMAIL_VALIDATION, context.getString(R.string.err_please_enter_email_address)))
            }
            !inputEmail.value?.let { CommonUtils.isValidEmailAddress(it) }!! -> {
                _forgotPasswordModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_EMAIL_VALIDATION, context.getString(R.string.err_please_enter_valid_email_address)))
            }
            !context.isNetworkAvailable() -> {
                _forgotPasswordModel.postValue(ResultOf.Validation(StatusCode.STATUS_CODE_INTERNET_VALIDATION, context.getString(R.string.err_please_check_your_internet_connection)))
            }
            else -> {
                _forgotPasswordModel.postValue(ResultOf.InProgress)
                val forgotPasswordRequestModel = ForgotPasswordRequestModel(inputEmail.value.toString())
                ForgotPasswordRepository.getInstance(_forgotPasswordModel).callForgotPasswordApi(forgotPasswordRequestModel)
            }
        }
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}