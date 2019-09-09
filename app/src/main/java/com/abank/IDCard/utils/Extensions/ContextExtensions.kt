package com.abank.IDCard.utils.Extensions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Vibrator
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Activity.hideKeyboardEx() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.apply { imm.hideSoftInputFromWindow(this.windowToken, 0) }
}

fun Activity.hideDialogKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

fun Activity.showKeyboardEx() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.apply { imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0); }
}

fun Context.showToast(text: Any) = Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT).show()

inline fun Activity.showSnack(message: String, isLong: Boolean = false, f: Snackbar.() -> Unit = {}) {
    (this.findViewById<View>(android.R.id.content))?.showSnack(message, isLong, f)
}

fun Context.getColorEx(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

fun Activity.copyToClipBoard(text: String, label: String = "simpleLabel") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.primaryClip = ClipData.newPlainText(label, text)
}

inline fun Context.isNetworkConnected(f: (Boolean) -> Unit) {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetInfo = connectivityManager.activeNetworkInfo
    f.invoke(activeNetInfo != null && activeNetInfo.isConnected)
}

fun Context.shortVibrate() = vibrate(100)
fun Context.midVibrate() = vibrate(200)
fun Context.vibrate(millis: Long) {
    try {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(millis)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// TODO: - uncomment if any sounds needed

//fun Context.soundReceived() = playSound(R.raw.income)
//fun Context.soundSend() = playSound(R.raw.outcome)
//private fun Context.playSound(@RawRes soundResource: Int) {
//    val isVibro = getShared(STATE_SETTINGS_VIBRO, false)
//    if (isVibro && soundResource != R.raw.outcome) {
//        vibrate(1000)
//    }
//    val isSound = getShared(STATE_SETTINGS_SOUND, false)
//    if (!isSound) return
//    val mp = MediaPlayer.create(this, soundResource)
//    mp.start()
//    mp.setOnCompletionListener {
//        it.release()
//    }
//}