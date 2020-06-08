package com.kotlin.architecture.api

import com.kotlin.architecture.BuildConfig
import com.network.okhttp.OkHttpClientFactory
import com.network.retrofit.RetrofitClientFactory
import okhttp3.OkHttpClient

object APIManager {

    fun <T> getRetrofitInstance(interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(BuildConfig.API_BASE_URL, interfaceClass,true)
    }

    fun <T> getRetrofitInstance(header: HashMap<String, String>, interfaceClass: Class<T>): T {
        return RetrofitClientFactory.getInstance(BuildConfig.API_BASE_URL,header, interfaceClass,true)
    }

    fun getOkHttpInstance(): OkHttpClient {
        return OkHttpClientFactory.getInstance(true)
    }

    fun getOkHttpInstance(header: HashMap<String, String>): OkHttpClient {
        return OkHttpClientFactory.getInstance(header, true)
    }
}