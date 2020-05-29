package com.arvatel.photoeditor

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

interface ImageFromActivityInterface {
    fun getMainImage() : Bitmap
    fun setMainImage(image : Bitmap)
    fun getTempImage() : Bitmap
    fun setTempImage(image : Bitmap)
    fun setBothImages(image : Bitmap)
    fun resizeImage(image: Bitmap) : Bitmap
}

class MainActivity : AppCompatActivity(), ImageFromActivityInterface {

    private lateinit var currentImage : Bitmap
    private lateinit var tempImage : Bitmap

    override fun getMainImage() : Bitmap {
        return currentImage
    }
    override fun setMainImage(image: Bitmap) {
        currentImage = image
    }
    override fun getTempImage() : Bitmap {
        return tempImage
    }
    override fun setTempImage(image: Bitmap) {
        tempImage = image
    }

    override fun setBothImages(image : Bitmap){
        currentImage = image
        tempImage = image
        tempImage = resizeImage(tempImage)
    }

    override fun resizeImage(image: Bitmap) : Bitmap {
        if (image.width < MAX_SIZE && image.height < MAX_SIZE) return image

        val max = if (image.width > image.height) image.width else image.height
        val cof : Double = MAX_SIZE.toDouble() / max
        return createScaledBitmap(image, (image.width * cof).toInt(), (image.height * cof).toInt(), false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
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
        private const val MAX_SIZE : Int = 1080
        private const val MY_PERMISSION_REQUEST_CODE = 1001
    }
}


