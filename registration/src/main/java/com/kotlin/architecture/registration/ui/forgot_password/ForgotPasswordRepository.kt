package com.kotlin.architecture.registration.ui.forgot_password


class ForgotPasswordRepository private constructor() {


    companion object {
        @Volatile
        private var instance: ForgotPasswordRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
                instance  ?: ForgotPasswordRepository().also { instance = it }
        }
    }
}