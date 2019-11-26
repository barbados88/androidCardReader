package com.abank.idcard.presentation.screens.auth.otp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.abank.idcard.R
import com.abank.idcard.utils.Extensions.textObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.pin_box_digit.view.*

class PinBoxDigitView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val disposer = CompositeDisposable()

    var nextPinBox: PinBoxDigitView? = null
        set(value) {
            value?.previousPinBox = this
            field = value
        }

    var text: String = ""

    var previousPinBox: PinBoxDigitView? = null

    var onLastInputed: () -> Unit = {}

    var onLastCleared: () -> Unit = {}

    var inFocus: Boolean = false
        set(value) {
            field = value
            etDigit.isFocusable = value
            etDigit.isFocusableInTouchMode = value
            if(value) etDigit.requestFocus()
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.pin_box_digit, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        etDigit.textObserver()
                .subscribe {
                    text = it
                    when(it.length) {
                        1 -> onTextInputed()
                    }
                }.addtoBag()

        etDigit.setOnKeyListener { _, keyCode, event ->
            if((keyCode == KeyEvent.KEYCODE_DEL).and(KeyEvent.ACTION_DOWN == event.action)) {
                if(text.isEmpty()) {
                    onTextRemoved()
                    return@setOnKeyListener true
                }
            }
            return@setOnKeyListener false
        }
    }

    private fun onTextInputed() {
        nextPinBox?.let {
           inFocus = false
            it.inFocus = true
        } ?: run { onLastInputed.invoke() }
    }

    private fun onTextRemoved() {
        previousPinBox?.let {
            inFocus = false
            it.inFocus = true
        }
        if(nextPinBox == null) onLastCleared.invoke()
    }

    fun clear() {
        disposer.dispose()
    }

    private fun Disposable.addtoBag() = disposer.add(this)


}