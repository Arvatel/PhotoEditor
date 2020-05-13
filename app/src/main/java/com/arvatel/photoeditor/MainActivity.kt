package com.arvatel.photoeditor

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

private val permissionList = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

private const val MY_PERMISSION_REQUEST_CODE = 1001

lateinit var currentImage : Bitmap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions(this)
    }
}

fun requestPermissions(activity : Activity) {
    ActivityCompat.requestPermissions(activity, permissionList, MY_PERMISSION_REQUEST_CODE)
}
