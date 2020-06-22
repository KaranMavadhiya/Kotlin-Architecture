package com.kotlin.architecture.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    sealed class ViewState {
        object Idle : ViewState()
        object InProgress : ViewState()
        data class Validate(var status: Int = 0, var message: Int) : ViewState()
        data class Succeed<T>(var data: T) : ViewState()
        data class Failed(var status: Int = 0, var message: String? = null) : ViewState()
    }
}