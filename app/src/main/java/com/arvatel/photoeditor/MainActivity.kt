package com.arvatel.photoeditor

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

interface ImageFromActivityInterface {
    fun getImage() : Bitmap
    fun setImage(image : Bitmap)
    fun getTempImage() : Bitmap
    fun setTempImage(image : Bitmap)
}

class MainActivity : AppCompatActivity(), ImageFromActivityInterface {

    private lateinit var currentImage : Bitmap
    private lateinit var tempImage : Bitmap

    override fun getImage() : Bitmap {
        return currentImage
    }
    override fun setImage(image: Bitmap) {
        currentImage = image
    }
    override fun getTempImage() : Bitmap {
        return tempImage
    }
    override fun setTempImage(image: Bitmap) {
        tempImage = image
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


