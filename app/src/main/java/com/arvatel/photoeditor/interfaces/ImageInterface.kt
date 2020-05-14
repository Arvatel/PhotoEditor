package com.arvatel.photoeditor.interfaces

import android.graphics.Bitmap

interface ImageInterface {
    var currentImage : Bitmap
    fun set(image : Bitmap) {
        currentImage = image
    }
}