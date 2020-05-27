package com.arvatel.photoeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_bottom_sheet_tool.view.*
import kotlinx.android.synthetic.main.fragment_photo_editor.view.*


class PhotoEditorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_editor, container, false)

        view.showImage.setImageBitmap((activity as ImageFromActivityInterface).getTempImage())

        view.buttonRotateBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_imageRotationFragment)
        }
        view.buttonRetouchBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_retouchImageFragment)
        }
        view.buttonScalingBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_imageScalingFragment)
        }
        view.buttonFilterBS.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_photoEditorFragment_to_filterFragment)
        }
        view.buttonExport.setOnClickListener {
            val w = (activity as ImageFromActivityInterface).getMainImage().width
            val h = (activity as ImageFromActivityInterface).getMainImage().height
            Toast.makeText(activity, "size: " + w.toString() + "x" + h.toString(), Toast.LENGTH_LONG).show()
        }

        return view
    }
}
