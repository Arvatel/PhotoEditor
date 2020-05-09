package com.arvatel.photoeditor.views.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.algorithms.OpenCvUtil
import kotlinx.android.synthetic.main.fragment_opencv.*
import org.opencv.android.OpenCVLoader


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
        OpenCVLoader.initDebug();
        val img = OpenCvUtil.searchForShapes(bitmap)
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
