package com.kotlin.architecture.registration.ui.signup

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData

class SignupInfo : BaseObservable() {

    @Bindable
    val inputFullName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val inputPassword = MutableLiveData<String>()



}