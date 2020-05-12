package com.arvatel.photoeditor

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission

private val permissionList = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

private const val MY_PERMISSION_REQUEST_CODE = 1001

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissionList, MY_PERMISSION_REQUEST_CODE)
    }
}
