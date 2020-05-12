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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context : Context = this
        if (checkPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE))
            Toast.makeText(context, "Storage, allow", Toast.LENGTH_LONG).show()
        else Toast.makeText(context, "Storage, deny", Toast.LENGTH_LONG).show()
        if (checkPermission(context, Manifest.permission.CAMERA))
            Toast.makeText(context, "Camera, allow", Toast.LENGTH_LONG).show()
        else Toast.makeText(context, "Camera, deny", Toast.LENGTH_LONG).show()
    }


}

fun checkPermission(context : Context, types : String) : Boolean {

    return when(checkSelfPermission(context, types)){
        PackageManager.PERMISSION_GRANTED -> true
        PackageManager.PERMISSION_DENIED -> false
        else -> false
    }
}
