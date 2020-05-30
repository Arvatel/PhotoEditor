package com.arvatel.photoeditor

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Bitmap.createScaledBitmap
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.opencv.android.OpenCVLoader

interface ImageFromActivityInterface {
    fun getMainImage() : Bitmap
    fun setMainImage(image : Bitmap)
    fun getTempImage() : Bitmap
    fun setTempImage(image : Bitmap)
    fun setBothImages(image : Bitmap)
    fun resizeImage(image: Bitmap, size: Int = MainActivity.MAX_SIZE): Bitmap
    fun beforeLaoding(view: View)
    fun afterLaoding(view: View)
    fun getThumbnail(): Bitmap
    fun setThumbnail(thumbnail: Bitmap)
}

class MainActivity : AppCompatActivity(), ImageFromActivityInterface {

    private lateinit var currentImage : Bitmap
    private lateinit var tempImage : Bitmap
    private lateinit var thumbnail: Bitmap

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
        thumbnail = resizeImage(tempImage, THUMPNAIL_SIZE)
    }

    override fun resizeImage(image: Bitmap, size: Int): Bitmap {
        if (image.width < size && image.height < size) return image

        val max = if (image.width > image.height) image.width else image.height
        val cof: Double = size.toDouble() / max
        return createScaledBitmap(image, (image.width * cof).toInt(), (image.height * cof).toInt(), false)
    }

    override fun beforeLaoding(view: View) {
        view.visibility = ProgressBar.VISIBLE
        (view as ProgressBar).setProgress(0, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    override fun afterLaoding(view: View) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        view.visibility = ProgressBar.GONE

    }

    override fun getThumbnail() = thumbnail
    override fun setThumbnail(thumbnail: Bitmap) {
        this.thumbnail = thumbnail
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissionList, MY_PERMISSION_REQUEST_CODE)
        OpenCVLoader.initDebug()
    }


    companion object{
        private val permissionList = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        public const val MAX_SIZE: Int = 1080
        private const val THUMPNAIL_SIZE: Int = 380
        private const val MY_PERMISSION_REQUEST_CODE = 1001
    }
}


