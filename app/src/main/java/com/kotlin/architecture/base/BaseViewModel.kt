package com.kotlin.architecture.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    sealed class ResultOf<out T> {
        data class Validation(val status : Int, val message: String): ResultOf<Nothing>()
        object InProgress : ResultOf<Nothing>()
        data class Success<out R>(val value: R): ResultOf<R>()
        data class Failure(val status : Int, val message: String?, val throwable: Throwable?): ResultOf<Nothing>()
    }
}