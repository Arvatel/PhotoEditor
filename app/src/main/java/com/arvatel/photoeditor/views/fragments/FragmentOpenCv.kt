package com.arvatel.photoeditor.views.fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.algorithms.OpenCvUtil
import com.arvatel.photoeditor.views.MainActivity
import kotlinx.android.synthetic.main.fragment_opencv.*
import org.opencv.android.OpenCVLoader
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream


private const val PHOTO_BITMAP = "photoBitmap"


class FragmentOpenCv : Fragment() {
    lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bitmap = it.getParcelable(PHOTO_BITMAP)!!
            bitmap = bitmap.copy(bitmap.config, true)//create copy of the original one not to modify the original one
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opencv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        OpenCVLoader.initDebug()
        val img = OpenCvUtil.searchForFaces(bitmap,
            (activity as MainActivity))
        opencvImageView.setImageBitmap(img)
    }

    companion object {
        @JvmStatic
        fun newInstance(bitmap: Bitmap) =
            FragmentOpenCv().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_BITMAP, bitmap)
                }
            }
    }
}
