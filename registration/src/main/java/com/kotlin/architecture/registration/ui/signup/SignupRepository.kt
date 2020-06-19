package com.kotlin.architecture.registration.ui.signup


class SignupRepository private constructor() {


    companion object {
        @Volatile
        private var instance: SignupRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
                instance  ?: SignupRepository().also { instance = it }
        }
    }
}