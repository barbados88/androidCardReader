package com.abank.IDCard.presentation.activity.scan

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import com.abank.IDCard.R
import com.abank.IDCard.presentation.base.BaseActivity
import com.abank.IDCard.presentation.custom_view.CameraPreview
import com.abank.IDCard.utils.Extensions.isVisible
import com.abank.IDCard.utils.Extensions.openApplicationSettings
import com.abank.IDCard.utils.Extensions.setNotDoubleClickListener
import com.fondesa.kpermissions.extension.onAccepted
import com.fondesa.kpermissions.extension.onDenied
import com.fondesa.kpermissions.extension.onPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.layout_permission_disabled.*
import java.io.IOException

class ScannerActivity: BaseActivity() {

    companion object {

        const val PERMISSION_REQUEST = 5

    }

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        initViews()
        checkPermissions()
    }

    private fun initViews() {
        buttonGiveCameraPermission.setNotDoubleClickListener(View.OnClickListener  { deniedButtonClick() })
        txtCreatePhoto.text = getString(R.string.scan_qr_code)
        txtAllowdCamera.text = getString(R.string.camera_permission_reason)
    }

    private fun setCameraLayout() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) return
        mCamera = getCameraInstance()
        mPreview = mCamera?.let {
            CameraPreview(this, it)
        }
        mPreview?.also {
            val preview: FrameLayout = findViewById(R.id.camera_preview)
            preview.addView(it)
        }
    }

    private fun getCameraInstance(): Camera? {
        return try {
            Camera.open()
        } catch (e: Exception) {
            null
        }
    }

    private fun checkPermissions() {
        val permissionRequest = permissionsBuilder(Manifest.permission.CAMERA).build()
        permissionRequest
                .onAccepted {
                    layoutPermissionCamera.isVisible(false)
                    setCameraLayout()
                }
                .onDenied { layoutPermissionCamera.isVisible(true) }
                .onPermanentlyDenied { layoutPermissionCamera.isVisible(true) }
        permissionRequest.send()
    }

    private fun deniedButtonClick() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            openApplicationSettings(PERMISSION_REQUEST, getString(R.string.qr_camera_permission))
        } else {
            checkPermissions()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PERMISSION_REQUEST -> {
                checkPermissions()
            }
        }
    }

}