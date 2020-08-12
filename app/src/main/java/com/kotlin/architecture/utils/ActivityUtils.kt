package com.kotlin.architecture.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * @param className Name of the next Activity (NextActivity::class.java)
 */
fun Context.startMyActivity(className: Class<*>?) {
    startActivity(Intent(applicationContext, className))
}

/**
 * @param classPath Path of the next Activity (xxx.xxx.NextActivity)
 */
fun Context.startMyActivity(classPath: String) {
    startActivity(Intent(Intent.ACTION_VIEW).setClassName(applicationContext, classPath))
}

/**
 * @param className  Name of the next Activity (NextActivity::class.java)
 * @param resultCode Activity result code
 */
fun Activity.startMyActivity(className: Class<*>?, resultCode: Int) {
    startActivityForResult(Intent(applicationContext, className), resultCode)
}

/**
 * @param classPath  Path of the next Activity (xxx.xxx.NextActivity)
 * @param resultCode Activity result code
 */
fun Activity.startMyActivity(classPath: String, resultCode: Int) {
    startActivityForResult(Intent(Intent.ACTION_VIEW).setClassName(applicationContext, classPath), resultCode)
}

/**
 * @param className Name of the next Activity (NextActivity::class.java)
 * @param bundle  Bundle which need to pass next Activity
 */
fun Context.startMyActivity(className: Class<*>?, bundle: Bundle?) {
    val intent = Intent(applicationContext, className)
    intent.putExtras(bundle!!)
    startActivity(intent)
}

/**
 * @param classPath Path of the next Activity (xxx.xxx.NextActivity)
 * @param bundle  Bundle which need to pass next Activity
 */
fun Context.startMyActivity(classPath: String, bundle: Bundle?) {
    val intent = Intent(Intent.ACTION_VIEW).setClassName(applicationContext, classPath)
    intent.putExtras(bundle!!)
    startActivity(intent)
}

/**
 * @param intent     Activity Intent
 * @param resultCode Activity result code
 */
fun Activity.startMyActivityForResultBack(intent: Intent?, resultCode: Int) {
    setResult(resultCode, intent)
}