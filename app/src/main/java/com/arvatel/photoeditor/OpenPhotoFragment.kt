package com.arvatel.photoeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_open_photo.view.*
import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.core.content.PermissionChecker
import androidx.core.graphics.drawable.toBitmap


class OpenPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_open_photo, container, false)

        val context : Context = requireContext()
        val activity : MainActivity = requireActivity() as MainActivity

        view.openFileLayout.setOnClickListener {
            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PermissionChecker.PERMISSION_DENIED) {
                activity.requestPermissions() // сделать нормально
            }
            else {
                activity.currentImage = resources.getDrawable(R.drawable.ic_launcher_foreground, null).toBitmap()
                Navigation.findNavController(view)
                    .navigate(R.id.action_openPhotoFragment_to_photoEditorFragment)
            }
        }

        view.openCameraLayout.setOnClickListener {
            if (PermissionChecker.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PermissionChecker.PERMISSION_DENIED) {
                activity.requestPermissions() // сделать нормально
            }
            else {
                activity.currentImage = resources.getDrawable(R.drawable.new_picture, null).toBitmap()
                Navigation.findNavController(view)
                    .navigate(R.id.action_openPhotoFragment_to_photoEditorFragment)
            }
        }
        return view
    }

}
