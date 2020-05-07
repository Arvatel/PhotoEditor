package com.arvatel.photoeditor.views.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.algorithms.Scaling
import kotlinx.android.synthetic.main.fragment_scall.*


private const val PHOTO_BITMAP = "photoBitmap"


class FragmentScall : Fragment() {
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


        button.setOnClickListener { getScalledPhotoByNewDimin() }
        button2.setOnClickListener { getScalledPhotoByRatio() }
    }

    private fun getScalledPhotoByRatio() {
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
            FragmentScall().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_BITMAP, bitmap)
                }
            }
    }
}
