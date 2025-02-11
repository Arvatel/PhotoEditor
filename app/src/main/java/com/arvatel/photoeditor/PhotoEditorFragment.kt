package com.arvatel.photoeditor

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_bottom_sheet_tool.view.*
import kotlinx.android.synthetic.main.fragment_photo_editor.*
import kotlinx.android.synthetic.main.fragment_photo_editor.view.*
import java.io.FileNotFoundException


class PhotoEditorFragment : Fragment() {
    lateinit var savePhoto: SavePhoto
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_editor, container, false)

        savePhoto= SavePhoto()

        view.showImage.setImageBitmap((activity as ImageFromActivityInterface).getMainImage())

        view.buttonRotateBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_imageRotationFragment)
        }
        view.buttonRetouchBS.setOnClickListener {
            //Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_retouchImageFragment)
            val text = "Retouch could be here! Call: 8-800-555-3535 :D"
            val duration = Toast.LENGTH_LONG
            Toast.makeText(context, text, duration).show()
        }
        view.buttonScalingBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_imageScalingFragment)
        }
        view.buttonFilterBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_filterFragment)
        }
        view.buttonFindShapesBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_findShapesFragment)
        }

        view.buttonExport.setOnClickListener {
            val str =savePhoto.saveImage(
                (activity as ImageFromActivityInterface).getMainImage(),
                context,
                "ourGreatApp"
            )
            Toast.makeText(context,"saved to: $str",Toast.LENGTH_SHORT).show()
        }

        view.buttonOpenPhoto.setOnClickListener {
            if (PermissionChecker.checkSelfPermission((context as Context), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PermissionChecker.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions((activity as Activity),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSION_REQUEST_CODE)
            }
            else {
                openPhoto()
            }
        }

        view.buttonTakePhoto.setOnClickListener {
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
        val packageManager : PackageManager = (activity as MainActivity).packageManager
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            PICK_IMAGE_CODE -> if (resultCode == Activity.RESULT_OK) {
                try {
                    val imageUri : Uri? = data?.data
                    (activity as ImageFromActivityInterface).setBothImages(
                        MediaStore.Images.Media.getBitmap((activity as Context).contentResolver, imageUri))

                    showImage.setImageBitmap((activity as ImageFromActivityInterface).getMainImage())
                }
                catch (e : FileNotFoundException){
                    e.printStackTrace()
                }
            }
            REQUEST_IMAGE_CAPTURE -> if (resultCode == Activity.RESULT_OK) {
                (activity as ImageFromActivityInterface).setBothImages(data?.extras?.get("data") as Bitmap)

                showImage.setImageBitmap((activity as ImageFromActivityInterface).getMainImage())
            }
        }
    }

    companion object {
        private const val MY_PERMISSION_REQUEST_CODE = 1001
        private const val PICK_IMAGE_CODE : Int = 1002
        private const val REQUEST_IMAGE_CAPTURE : Int = 1003
    }
}
