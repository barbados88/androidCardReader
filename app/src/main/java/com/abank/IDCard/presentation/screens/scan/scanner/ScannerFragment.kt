package com.abank.idcard.presentation.screens.scan.scanner

import android.Manifest
import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceHolder
import android.view.View
import androidx.core.app.ActivityCompat
import com.abank.idcard.R
import com.abank.idcard.presentation.base.BaseFragment
import com.abank.idcard.utils.Extensions.isVisible
import com.abank.idcard.utils.Extensions.openApplicationSettings
import com.abank.idcard.utils.Extensions.setNotDoubleClickListener
import com.fondesa.kpermissions.extension.onAccepted
import com.fondesa.kpermissions.extension.onDenied
import com.fondesa.kpermissions.extension.onPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import kotlinx.android.synthetic.main.activity_scanner.*
import kotlinx.android.synthetic.main.layout_permission_disabled.*
import org.koin.android.ext.android.get
import java.io.IOException

class ScannerFragment : BaseFragment(), SurfaceHolder.Callback  {

    override fun getLayout() = R.layout.fragment_scanner

    override val navigator: ScannerNavigator = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed( {startSettings() }, 100)
    }

    private lateinit var camera: Camera
    private lateinit var mCameraSource: CameraSource
    private lateinit var surfaceHolder: SurfaceHolder

    private val cameraResolution: Pair<Int, Int>
        get() {
            val sizes = camera.parameters.supportedPreviewSizes
            return Pair(sizes.first().width, sizes.first().height)
        }

    private fun startSettings() {
        initViews()
        checkPermissions()
    }

    private fun initViews() {
//        recognizedTextView.text = "IDUKR00373896441988043000139<<\n8804307M2908096UKR<<<<<<<<<<<4\nZAKHAROV<<PAVLO<<<<<<<<<<<<<<"
        buttonGiveCameraPermission.setNotDoubleClickListener(View.OnClickListener  { deniedButtonClick() })
        txtCreatePhoto.text = getString(R.string.scan_qr_code)
        txtAllowdCamera.text = getString(R.string.camera_permission_reason)
    }

    // TODO: - hide in CameraPreview
    // handle callbacks in activity

    private fun checkPermissions() {
        val permissionRequest = permissionsBuilder(Manifest.permission.CAMERA).build()
        permissionRequest
                .onAccepted {
                    layoutPermissionCamera.isVisible(false)
                    startCameraSource()
                }
                .onDenied { layoutPermissionCamera.isVisible(true) }
                .onPermanentlyDenied { layoutPermissionCamera.isVisible(true) }
        permissionRequest.send()
    }

    private fun deniedButtonClick() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA)) {
            requireActivity().openApplicationSettings(PERMISSION_REQUEST, getString(R.string.qr_camera_permission))
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

    override fun surfaceCreated(holder: SurfaceHolder) {
        checkPermissions()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mCameraSource.stop()
    }

    private fun startCamera() {
        camera = Camera.open()
        camera.setDisplayOrientation(90)
        try {
            camera.setPreviewDisplay(surfaceHolder)
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

//    https://stackoverflow.com/questions/44918705/google-ocr-working-on-specific-area

    private fun startCameraSource() {
        surfaceHolder = camera_preview.holder
        surfaceHolder.addCallback(this)
        startCamera()
        val textRecognizer = TextRecognizer.Builder(requireContext()).build()
        if (!textRecognizer.isOperational) return
        val resolution = cameraResolution
        mCameraSource = CameraSource.Builder(requireContext(), textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(resolution.first, resolution.second)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build()
        mCameraSource.start(surfaceHolder)
        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                val items = detections.detectedItems
                if (items.size() != 0) {

                    recognizedTextView.post {
                        val stringBuilder = StringBuilder()
                        for (i in 0 until items.size()) {
                            val item = items.valueAt(i)
                            if (item.value.contains("IDUKR")) {
                                stringBuilder.append(item.value)
                            }
                        }
                        val recognized = stringBuilder.toString().replace(" ", "")
                        val regex = "[A-Z]{1,}[0-9]{23}.*\\n[0-9]{7}.[0-9]{7}[A-Z]{3}.*\\n[A-Z]{1,}<<[A-Z]{1,}.*".toRegex()
                        if (recognized.matches(regex)) {
                            recognizedTextView.text = recognized
                            Handler().postDelayed({
                                returnResult(recognized)

                            }, 2000)

                        }
                    }
                }
            }
        })
    }

    private fun returnResult(string: String) {
        navigator.backWithResult(string)
    }

    companion object {

        const val PERMISSION_REQUEST = 5

        fun newInstance() = ScannerFragment()

    }

}