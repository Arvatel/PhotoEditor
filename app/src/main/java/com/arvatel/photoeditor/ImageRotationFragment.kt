package com.arvatel.photoeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_image_rotation.view.*

class ImageRotationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_rotation, container, false)

        view.buttonCancel.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }
        view.buttonApply.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }

        return view
    }
}
