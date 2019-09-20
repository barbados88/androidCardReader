package com.abank.IDCard.utils

import android.content.Context
import android.content.SharedPreferences

var sharedPrefs: SharedPreferences? = null

fun Context.getPrefs(): SharedPreferences = sharedPrefs ?: getSharedPreferences("incognito", Context.MODE_PRIVATE).also { sharedPrefs = it }

inline fun String.onChanged(crossinline valueChanged: (String) -> Unit) {
    sharedPrefs?.registerOnSharedPreferenceChangeListener { _, key ->
        run { if (key == this) valueChanged.invoke(key) }
    }
}

inline fun <reified T : Any> Context.getShared(key: String, defValue: T, crossinline observer: (T) -> Unit = {}): T {
    val resultValue = getCurrentValue(key, defValue)
    key.onChanged { observer.invoke(getCurrentValue(key, defValue)) }
    observer.invoke(resultValue)
    return resultValue
}

inline fun <reified T : Any> Context.getCurrentValue(key: String, defValue: T): T {
    getPrefs().run {
        when (defValue) {
            is Boolean -> return getBoolean(key, defValue) as T
            is Long -> return getLong(key, defValue) as T
            is Int -> return getInt(key, defValue) as T
            is String -> return getString(key, defValue) as T
            is Float -> return getFloat(key, defValue) as T
            else -> throw Exception("Cannot cast to any type")
        }
    }
}

inline fun <reified T : Any> Context.setShared(key: String, newValue: T) {
    getPrefs().edit {
        when (newValue) {
            is Long -> putLong(key, newValue)
            is Int -> putInt(key, newValue)
            is String -> putString(key, newValue)
            is Boolean -> putBoolean(key, newValue)
            is Float -> putFloat(key, newValue)
            else -> throw Exception("Cannot cast to any type")
        }
    }
}

inline fun SharedPreferences.edit(crossinline action: SharedPreferences.Editor.() -> Unit) {
    edit().apply {
        action(this)
        apply()
    }
}