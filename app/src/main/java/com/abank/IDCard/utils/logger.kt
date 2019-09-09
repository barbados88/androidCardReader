package com.abank.IDCard.utils

import android.util.Log
import com.abank.IDCard.BuildConfig

private const val TAG = "Logger"

fun logError(exception: Throwable, tag: String = TAG) = whenDebug { Log.e(tag, exception.message, exception) }

fun logError(message: String, tag: String = TAG) = whenDebug { Log.e(tag, message) }

fun logDebug(exception: Throwable, tag: String = TAG) = whenDebug { Log.d(tag, exception.message, exception) }

fun logDebug(message: String, tag: String = TAG) = whenDebug { Log.d(tag, message) }

fun logInfo(exception: Throwable, tag: String = TAG) = whenDebug { Log.i(tag, exception.message, exception) }

fun logInfo(message: String, tag: String = TAG) = whenDebug { Log.i(tag, message) }

fun logVerbose(exception: Throwable, tag: String = TAG) = whenDebug { Log.v(tag, exception.message, exception) }

fun logVerbose(message: String, tag: String = TAG) = whenDebug { Log.v(tag, message) }

fun logWarn(exception: Throwable, tag: String = TAG) = whenDebug { Log.w(tag, exception.message, exception) }

fun logWarn(message: String, tag: String = TAG) = whenDebug { Log.w(tag, message) }

private inline fun whenDebug(f: () -> Unit) {
    if (BuildConfig.DEBUG) f.invoke()
}

private fun checkNotNull(string: String): String = if (string.isEmpty()) "string for log is null" else string