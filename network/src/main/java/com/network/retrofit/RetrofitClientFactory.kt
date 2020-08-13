package com.network.retrofit

import com.network.okhttp.OkHttpClientFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClientFactory {

    private fun getService(baseUrl: String, header: HashMap<String, String>? = null, isDebug: Boolean = false): Retrofit {

        val moshiConverter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClientFactory.getInstance(header, isDebug))
            .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
            .build()
    }

    fun <T> getInstance( baseUrl: String, header: HashMap<String, String>? = null, interfaceClass: Class<T>, isDebug: Boolean = false): T {
        return getService(baseUrl, header, isDebug).create(interfaceClass)
    }
}