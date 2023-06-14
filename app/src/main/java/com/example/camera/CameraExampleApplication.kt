package com.example.camera

import android.app.Application
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig

class CameraExampleApplication: Application(), CameraXConfig.Provider {
    override fun getCameraXConfig(): CameraXConfig = Camera2Config.defaultConfig()

}
