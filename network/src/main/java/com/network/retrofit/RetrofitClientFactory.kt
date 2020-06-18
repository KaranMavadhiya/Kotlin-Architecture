package com.network.retrofit

import com.network.okhttp.OkHttpClientFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClientFactory {

    private fun getService(baseUrl: String, header: HashMap<String, String>?, isDebug: Boolean): Retrofit {

        val moshiConverter = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClientFactory.getInstance(header, isDebug))
            .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
            .build()
    }

    /*
     * default value of header: HashMap<String, String> is null
     * default value of isDebug: Boolean is false
     */
    fun <T> getInstance(baseUrl: String, interfaceClass: Class<T>): T {
        return getService(baseUrl, null, false).create(interfaceClass)
    }

    /*
     * default value of isDebug: Boolean is false
     */
    fun <T> getInstance(baseUrl: String, header: HashMap<String, String>, interfaceClass: Class<T>): T {
        return getService(baseUrl, header, false).create(interfaceClass)
    }

    /*
     * default value of header: HashMap<String, String> is null
     */
    fun <T> getInstance(baseUrl: String, interfaceClass: Class<T>, isDebug: Boolean): T {
        return getService(baseUrl, null, isDebug).create(interfaceClass)
    }

    fun <T> getInstance( baseUrl: String, header: HashMap<String, String>, interfaceClass: Class<T>, isDebug: Boolean): T {
        return getService(baseUrl, header, isDebug).create(interfaceClass)
    }
}