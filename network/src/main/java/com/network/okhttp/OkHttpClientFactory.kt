package com.network.okhttp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {

    private const val CONNECTION_TIME_OUT: Long = 1
    private lateinit var okHttpInstance: OkHttpClient.Builder

    fun getInstance(header: HashMap<String, String>?, isDebug: Boolean): OkHttpClient {

        okHttpInstance = OkHttpClient().newBuilder()
        okHttpInstance.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MINUTES)

        // Add Header if header != null and header.size > 0
        if (header != null && header.size > 0) {
            okHttpInstance.addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                header.entries.forEach { (key, value) ->
                    builder.addHeader(key, value)
                }
                builder.build()
                chain.proceed(builder.build())
            }
        }

        // Add HttpLoggingInterceptor if isDebug true
        if (isDebug) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpInstance.addInterceptor(loggingInterceptor)
        }

        return okHttpInstance.build()
    }

    /*
     * default value of header: HashMap<String, String> is null
     * default value of isDebug: Boolean is false
     */
    fun getInstance(): OkHttpClient {
        return getInstance(null, false)
    }

    /*
     * default value of header: HashMap<String, String> is null
     */
    fun getInstance(isDebug: Boolean): OkHttpClient {
        return getInstance(null, isDebug)
    }

    /*
     * default value of isDebug: Boolean is false
     */
    fun getInstance(header: HashMap<String, String>): OkHttpClient {
        return getInstance(header, false)
    }
}