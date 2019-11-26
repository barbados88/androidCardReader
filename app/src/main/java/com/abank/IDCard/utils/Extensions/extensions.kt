package com.abank.idcard.utils.Extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.abank.idcard.R
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AppCompatActivity.hideKeyboardEx() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.apply { imm.hideSoftInputFromWindow(windowToken, 0) }
}

fun Activity.openApplicationSettings( code: Int = 9, message: String) {
    val appSettingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
    startActivityForResult(appSettingsIntent, code)
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

inline fun postDelayed(millis: Long, onMainThread: Boolean = false, crossinline f: () -> Unit) {
    val handler = if (onMainThread) Handler(Looper.getMainLooper()) else Handler()
    handler.postDelayed({ f.invoke() }, millis)
}

fun FragmentManager.replaceFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean, needAnimate: Boolean) {
    var ft = this.beginTransaction()
    val fragmentName = fragment.javaClass.simpleName
    if (addToBackStack) ft = ft.addToBackStack(fragmentName)
    if (needAnimate) ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.pop_out_right, R.animator.pop_in_left)
    ft.replace(containerViewId, fragment, fragmentName).commit()
}

fun Activity.showToast(text: Any) = Toast.makeText(this, text.toString(), Toast.LENGTH_SHORT).show()
fun Activity.showSnack(text: String) = Snackbar.make(this.findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG).show()
fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT) = Snackbar.make(this, message, length).show()
inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}

fun EditText.textObserver(seconds: Long = 0): Observable<String> {

    val textChangeObservable = Observable.create<String> { emitter ->
        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s?.toString()?.let { emitter.onNext(it) }
            }

        }

        addTextChangedListener(textWatcher)

        emitter.setCancellable {
            removeTextChangedListener(textWatcher)
        }
    }

    return if (seconds.toInt() == 0) textChangeObservable else textChangeObservable.debounce(seconds, TimeUnit.SECONDS)

}

fun TextInputEditText.textObserver(seconds: Long = 0): Observable<String> {

    val textChangeObservable = Observable.create<String> { emitter ->
        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s?.toString()?.let { emitter.onNext(it) }
            }

        }

        addTextChangedListener(textWatcher)

        emitter.setCancellable {
            removeTextChangedListener(textWatcher)
        }
    }

    return if (seconds.toInt() == 0) textChangeObservable else textChangeObservable.debounce(seconds, TimeUnit.SECONDS)
}

fun TextInputLayout.getText(): String? {
    return editText?.text.toString()
}