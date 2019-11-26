package com.abank.idcard.utils.Extensions

import androidx.fragment.app.FragmentManager

inline fun <reified T> FragmentManager.findAttached(tag: String?): T? {
    val found = findFragmentByTag(tag)
    return found as? T
}
