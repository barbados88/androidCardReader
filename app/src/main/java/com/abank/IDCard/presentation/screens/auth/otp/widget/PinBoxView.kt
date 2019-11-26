package com.abank.idcard.presentation.screens.auth.otp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.abank.idcard.R
import kotlinx.android.synthetic.main.pin_box.view.*

class PinBoxView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): RelativeLayout(context, attrs, defStyleAttr) {

    var onInputted: (String) -> Unit = { }
    var onLastCleared: () -> Unit = {}
    val text: String?
        get() = pbFirst.text + pbSecond.text + pbThird.text + pbFourth.text

    init {
        LayoutInflater.from(context).inflate(R.layout.pin_box, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        init()
    }

    private fun init() {
        pbFirst?.nextPinBox = pbSecond
        pbSecond?.nextPinBox = pbThird
        pbThird?.nextPinBox = pbFourth

        pbFirst?.inFocus = true
        pbFourth?.onLastInputed = { onInputted.invoke(pbFirst.text + pbSecond.text + pbThird.text + pbFourth.text) }
        pbFourth?.onLastCleared = onLastCleared
    }

}