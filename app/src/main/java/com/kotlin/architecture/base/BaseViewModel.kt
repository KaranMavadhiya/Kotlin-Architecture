package com.kotlin.architecture.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.architecture.utils.preferences.PreferenceConstant
import com.kotlin.architecture.utils.preferences.getString
import com.kotlin.architecture.utils.preferences.putString
import java.util.*

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    var stateMutableLiveData = MutableLiveData<ViewState>(ViewState.Idle)
    val stateLiveData : LiveData<ViewState> = stateMutableLiveData

    sealed class ViewState {
        object Idle : ViewState()
        object InProgress : ViewState()
        data class Succeed<T>(var data: T) : ViewState()
        data class Failed ( var status: Int = 0, var message: String? = null) : ViewState()
    }

    companion object {
        /**
         * As the requirement for most of applications is to identify a particular installation and not a physical device,
         * a good solution to get unique id for an user if to use UUID class. The following solution has been presented by
         * Reto Meier from Google in a Google I/O presentation
         *
         * @return The value from shared preferences, or the provided default.
         */
        @get:Synchronized
        val uniqueId: String
            get() {
                var uniqueID = PreferenceConstant.UNIQUE_ID.getString(null)
                if (uniqueID == null) {
                    uniqueID = UUID.randomUUID().toString()
                    PreferenceConstant.UNIQUE_ID.putString(uniqueID)
                }
                return uniqueID
            }
    }
}