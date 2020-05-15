package com.arvatel.photoeditor

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_open_photo.view.*
import java.io.FileNotFoundException


class OpenPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_open_photo, container, false)

        view.openFileLayout.setOnClickListener {
            if (PermissionChecker.checkSelfPermission((context as Context), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PermissionChecker.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions((activity as Activity),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_CODE)
            }
            else {
                openPhoto()
            }
        }

        view.openCameraLayout.setOnClickListener {
            if (PermissionChecker.checkSelfPermission((context as Context), Manifest.permission.CAMERA) ==
                PermissionChecker.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions((activity as Activity),
                    arrayOf(Manifest.permission.CAMERA), MY_PERMISSION_REQUEST_CODE)
            }
            else {
                openCamera()
            }
        }
        return view
    }

    private fun openPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            PICK_IMAGE_CODE -> if (resultCode == RESULT_OK) {
                try {
                    val imageUri : Uri? = data?.data
                    (activity as ImageFromActivityInterface).setImage(
                        MediaStore.Images.Media.getBitmap((activity as Context).contentResolver, imageUri))
                    Navigation.findNavController(view as View)
                        .navigate(R.id.action_openPhotoFragment_to_photoEditorFragment)
                }
                catch (e : FileNotFoundException){
                    e.printStackTrace()
                }
            }
            CAMERA_REQUEST -> if (resultCode == RESULT_OK) {
                (activity as ImageFromActivityInterface).setImage(data!!.extras!!["data"] as Bitmap)
                Navigation.findNavController(view as View)
                    .navigate(R.id.action_openPhotoFragment_to_photoEditorFragment)
            }
        }
    }

    companion object {
        private const val MY_PERMISSION_REQUEST_CODE = 1001
        private const val PICK_IMAGE_CODE : Int = 1002
        private const val CAMERA_REQUEST : Int = 1003
    }
}
