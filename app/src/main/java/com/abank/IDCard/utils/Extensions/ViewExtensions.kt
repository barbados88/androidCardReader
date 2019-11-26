package com.abank.idcard.utils.Extensions

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showToast(text: Any) = Toast.makeText(this.context, text.toString(), Toast.LENGTH_SHORT).show()

inline fun View.showSnack(message: String, isLong: Boolean = false, f: Snackbar.() -> Unit = {}) {
    val snack = Snackbar.make(this, "<font color=\"#549bdf\">$message</font>".fromHtml(), if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT)
    snack.f()
    snack.show()
}

fun View.setNotDoubleClickListener(listener: View.OnClickListener) {
    var lastClickTime = 0L
    setOnClickListener { view ->
        if (System.currentTimeMillis() > lastClickTime + 1000) {
            listener.onClick(view)
            lastClickTime = System.currentTimeMillis()
        }
    }
}

fun View.isVisible(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}