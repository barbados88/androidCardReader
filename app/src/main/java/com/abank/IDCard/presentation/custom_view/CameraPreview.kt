package com.abank.idcard.presentation.custom_view

import android.content.Context
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.google.android.gms.vision.CameraSource
import java.io.IOException

class CameraPreview(context: Context, private val mCamera: SurfaceView, private val source: CameraSource) : SurfaceView(context), SurfaceHolder.Callback {

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            source.start(mCamera.holder)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        source.stop()
    }

}