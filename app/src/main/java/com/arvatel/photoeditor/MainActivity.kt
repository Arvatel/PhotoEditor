package com.arvatel.photoeditor

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.arvatel.photoeditor.interfaces.ImageInterface

interface StorageInterface {
    fun getImage() : Bitmap
    fun setImage(image : Bitmap)
}

class MainActivity : AppCompatActivity(), StorageInterface {

    private lateinit var currentImage : Bitmap

    override fun getImage() : Bitmap {
        return currentImage
    }

    override fun setImage(image: Bitmap) {
        currentImage = image
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissionList, MY_PERMISSION_REQUEST_CODE)
    }

    companion object{
        private val permissionList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )

        private const val MY_PERMISSION_REQUEST_CODE = 1001
    }
}


