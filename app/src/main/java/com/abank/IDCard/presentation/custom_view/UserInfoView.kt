package com.abank.idcard.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.abank.idcard.R
import com.abank.idcard.presentation.screens.scan.select.model.ProfileModel
import kotlinx.android.synthetic.main.layout_info_view.view.*

class UserInfoView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    var listener: (() -> Unit)? = null

    var userInfo: ProfileModel? = null
        set(value) {
            value?.let { handleProfileInfo(it) }
            field = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_info_view, this, true)
    }

    private fun addListeners() {
        settingsImageView.setOnClickListener { listener?.invoke() }
    }

    fun fillInfo() {
        addListeners()
    }

    private fun handleProfileInfo(profileInfo: ProfileModel) {
        nameTextView.text = profileInfo.namesInfo
    }

}