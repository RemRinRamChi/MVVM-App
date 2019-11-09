package com.yawjenn.mvvmpractice.util

import android.util.Log

/**
 * Logs the String at INFO level
 */
fun String.log(tag: String = "DEV LOG") = Log.i(tag, this)

/**
 * Logs the String at ERROR level
 */
fun String.logError(tag: String = "DEV LOG") = Log.e(tag, this)