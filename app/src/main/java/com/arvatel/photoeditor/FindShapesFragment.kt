package com.arvatel.photoeditor

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.arvatel.photoeditor.algorithms.OpenCvUtil
import kotlinx.android.synthetic.main.fragment_find_shapes.view.*
import org.opencv.android.OpenCVLoader


class FindShapesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_shapes, container, false)
        var tempImage : Bitmap = (activity as ImageFromActivityInterface).getTempImage().copy(Bitmap.Config.ARGB_8888, false)
        var isFaces : Boolean = false
        var isShapes : Boolean = false

        Log.e("amrr ", OpenCVLoader.initDebug().toString())
        view.showImageFindShapes.setImageBitmap((activity as ImageFromActivityInterface).getTempImage())

        view.buttonApplyFindShapes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_findShapesFragment_to_photoEditorFragment)
        }
        view.buttonCancelFindShapes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_findShapesFragment_to_photoEditorFragment)
        }

        view.buttonFindFaces.setOnClickListener {
            if (!isFaces) {
                view.showImageFindShapes.setImageBitmap(OpenCvUtil.searchForFaces(tempImage, context))
                isFaces = true
            }
        }
        view.buttonFindBasicShapes.setOnClickListener {
            if (!isShapes) {
                view.showImageFindShapes.setImageBitmap(OpenCvUtil.searchForShapes(tempImage))
                isShapes = true
            }
        }
        view.buttonCleanShapes.setOnClickListener {
            tempImage = (activity as ImageFromActivityInterface).getTempImage().copy(Bitmap.Config.ARGB_8888, false)
            view.showImageFindShapes.setImageBitmap((activity as ImageFromActivityInterface).getTempImage())
            isFaces = false
            isShapes = false
        }

        return view
    }

}
