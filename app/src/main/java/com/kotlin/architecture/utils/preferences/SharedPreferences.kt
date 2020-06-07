package com.kotlindemo.utils.preferences

fun String.getBoolean(defaultValue: Boolean = false): Boolean? {
    return ApplicationPreferences.sharedPreferences?.getBoolean(this,defaultValue)
}

fun String.putBoolean(value: Boolean){
    ApplicationPreferences.sharedPreferences?.edit()?.putBoolean(this, value)?.apply()
}

fun String.getInteger(defaultValue: Int = 0): Int? {
    return ApplicationPreferences.sharedPreferences?.getInt(this,defaultValue)
}

fun String.putInteger(value: Int){
    ApplicationPreferences.sharedPreferences?.edit()?.putInt(this, value)?.apply()
}

fun String.getFloat(defaultValue: Float = 0F): Float? {
    return ApplicationPreferences.sharedPreferences?.getFloat(this,defaultValue)
}

fun String.putFloat(value: Float){
    ApplicationPreferences.sharedPreferences?.edit()?.putFloat(this, value)?.apply()
}

fun String.getLong(defaultValue: Long = -0L): Long? {
    return ApplicationPreferences.sharedPreferences?.getLong(this,defaultValue)
}

fun String.putLong(value: Long){
    ApplicationPreferences.sharedPreferences?.edit()?.putLong(this, value)?.apply()
}

fun String.getString(defaultValue: String? = null): String? {
    return ApplicationPreferences.sharedPreferences?.getString(this,defaultValue)
}

fun String.putString(value: String){
    ApplicationPreferences.sharedPreferences?.edit()?.putString(this, value)?.apply()
}