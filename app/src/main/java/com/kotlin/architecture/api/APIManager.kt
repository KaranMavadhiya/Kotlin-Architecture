package com.kotlin.architecture.api

import com.kotlin.architecture.BuildConfig
import com.network.okhttp.OkHttpClientFactory
import com.network.retrofit.RetrofitClientFactory
import okhttp3.OkHttpClient

object APIManager {

    private const val baseUrl = BuildConfig.API_BASE_URL
    private const val isDebug = true

    fun <T> getRetrofitInstance(interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(baseUrl, interfaceClass,isDebug)
    }

    fun <T> getRetrofitInstance(header: HashMap<String, String>, interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(baseUrl,header, interfaceClass,isDebug)
    }

    fun getOkHttpInstance(): OkHttpClient {
        return OkHttpClientFactory.getInstance(isDebug)
    }

    fun getOkHttpInstance(header: HashMap<String, String>): OkHttpClient {
        return OkHttpClientFactory.getInstance(header, isDebug)
    }
}