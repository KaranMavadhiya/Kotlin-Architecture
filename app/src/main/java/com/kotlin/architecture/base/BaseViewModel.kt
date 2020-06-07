package com.kotlin.architecture.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlindemo.utils.preferences.PreferenceConstant
import com.kotlindemo.utils.preferences.getString
import com.kotlindemo.utils.preferences.putString
import java.util.*

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private var isLoading = MutableLiveData<Boolean>()

    fun isLoading(): LiveData<Boolean> {
        return isLoading
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
                var uniqueID = PreferenceConstant.PREF_UNIQUE_ID.getString(null)
                if (uniqueID == null) {
                    uniqueID = UUID.randomUUID().toString()
                    PreferenceConstant.PREF_UNIQUE_ID.putString(uniqueID)
                }
                return uniqueID
            }
    }
}