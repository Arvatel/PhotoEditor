package com.arvatel.photoeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        var tempImage = (activity as ImageFromActivityInterface).getTempImage()

        OpenCVLoader.initDebug()
        view.showImageFindShapes.setImageBitmap((activity as ImageFromActivityInterface).getTempImage())
        view.buttonApplyFindShapes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_findShapesFragment_to_photoEditorFragment)
        }
        view.buttonCancelFindShapes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_findShapesFragment_to_photoEditorFragment)
        }

        view.buttonFindFaces.setOnClickListener {
            tempImage = OpenCvUtil.searchForFaces((activity as ImageFromActivityInterface).getTempImage(), context)
            view.showImageFindShapes.setImageBitmap(tempImage)
        }
        view.buttonFindBasicShapes.setOnClickListener {
            tempImage = OpenCvUtil.searchForShapes((activity as ImageFromActivityInterface).getTempImage())
            view.showImageFindShapes.setImageBitmap(tempImage)
        }

        return view
    }

}
