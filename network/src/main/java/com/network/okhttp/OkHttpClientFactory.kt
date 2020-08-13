package com.network.okhttp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {

    private const val CONNECTION_TIME_OUT: Long = 30
    private lateinit var okHttpInstance: OkHttpClient.Builder

    fun getInstance(header: HashMap<String, String>? = null, isDebug: Boolean = false): OkHttpClient {

        okHttpInstance = OkHttpClient().newBuilder()
        okHttpInstance.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)

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
}