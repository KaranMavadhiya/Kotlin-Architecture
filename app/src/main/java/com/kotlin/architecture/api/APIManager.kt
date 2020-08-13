package com.kotlin.architecture.api

import com.kotlin.architecture.BuildConfig
import com.network.okhttp.OkHttpClientFactory
import com.network.retrofit.RetrofitClientFactory
import okhttp3.OkHttpClient

object APIManager {

    private const val baseUrl = BuildConfig.API_BASE_URL
    private const val isDebug = true

    fun <T> getRetrofitInstance(header: HashMap<String, String>? = null, interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(baseUrl = baseUrl, header = header, interfaceClass = interfaceClass, isDebug = isDebug)
    }

    fun <T> getRetrofitInstance(interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(baseUrl = baseUrl, interfaceClass = interfaceClass, isDebug = isDebug)
    }
}