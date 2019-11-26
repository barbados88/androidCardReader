package com.example.data.prefs

import android.content.Context
import android.content.SharedPreferences

interface SharedPreferencesProvider {

    fun storeToken(token: String)
    fun getToken(): String

}

class SharedPreferencesProviderImpl(context: Context): SharedPreferencesProvider {

    private val sharedPrefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    override fun getToken(): String  = getCurrentValue(TOKEN, "")

    override fun storeToken(token: String) = setShared(TOKEN, token)

    private inline fun <reified T : Any> getCurrentValue(key: String, defValue: T): T {
        sharedPrefs.apply {
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

    private inline fun <reified T : Any> setShared(key: String, newValue: T) {
        sharedPrefs.edit {
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

    private inline fun SharedPreferences.edit(crossinline action: SharedPreferences.Editor.() -> Unit) {
        edit().apply {
            action(this)
            apply()
        }
    }

    companion object {
        private const val PREFS = "prefs"
        private const val TOKEN = "access_token"
    }

}