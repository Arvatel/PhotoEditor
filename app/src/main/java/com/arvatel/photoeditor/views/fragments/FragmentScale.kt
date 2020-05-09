package com.arvatel.photoeditor.views.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.algorithms.Scaling
import com.arvatel.photoeditor.views.MainActivity
import kotlinx.android.synthetic.main.fragment_rotation.*
import kotlinx.android.synthetic.main.fragment_scall.*


private const val PHOTO_BITMAP = "photoBitmap"


class FragmentScale : Fragment() {
    lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bitmap = it.getParcelable(PHOTO_BITMAP)!!

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scall, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scallImageView.setImageBitmap(bitmap)


        byDimBV.setOnClickListener { getScalledPhotoByNewDimin() }
        byRatioBV.setOnClickListener { getScaldedPhotoByRatio() }

        scallFinishBV.setOnClickListener { finishFragment() }
        scallDiscaredBV.setOnClickListener {
            (activity as MainActivity).closeFragment(this)
        }
    }

    private fun finishFragment() {
        (activity as MainActivity).closeFragment(this)
        (activity as MainActivity).showTheNewImage(scallImageView.drawable.toBitmap())
    }


    private fun getScaldedPhotoByRatio() {
        val vit =
            Scaling.nearestNeighborScalingRatio(bitmap, (editText3.text.toString()).toDouble());
        scallImageView.setImageBitmap(vit)
    }

    private fun getScalledPhotoByNewDimin() {
        val width = Integer.valueOf(editText.text.toString())
        val height = Integer.valueOf(editText2.text.toString())
        val vit = Scaling.nearestNeighborScaling(bitmap, width, height);
        scallImageView.setImageBitmap(vit)
    }

    companion object {
        @JvmStatic
        fun newInstance(bitmap: Bitmap) =
            FragmentScale().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_BITMAP, bitmap)
                }
            }
    }
}
