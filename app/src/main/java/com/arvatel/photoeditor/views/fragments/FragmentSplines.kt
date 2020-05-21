package com.arvatel.photoeditor.views.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.views.MainActivity
import kotlinx.android.synthetic.main.fragment_splines.*


private const val PHOTO_BITMAP = "photoBitmap"


class FragmentSplines : Fragment() {
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
        return inflater.inflate(R.layout.fragment_splines, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        splinesFinishBV.setOnClickListener { finishFragment() }
        splinesClearBV.setOnClickListener {
            (activity as MainActivity).closeFragment(this)
        }
        drawLineBV.setOnClickListener { v -> callOnDraw(v) }
        drawCubicSplineBV.setOnClickListener { v -> callOnDraw(v) }
        splinesClearBV.setOnClickListener{clean()   }
    }

    private fun callOnDraw(view: View) {
        when (view as Button) {
            drawLineBV -> splinesIV.callOnDraw(false, true)
            drawCubicSplineBV -> splinesIV.callOnDraw(true, false)
        }
    }
    private fun clean(){
        splinesIV.clean()
    }
    //todo save image
    private fun finishFragment() {
        (activity as MainActivity).closeFragment(this)
//        (activity as MainActivity).showTheNewImage(filterFragmentImageView.drawable.toBitmap())
    }


    companion object {
        @JvmStatic
        fun newInstance(bitmap: Bitmap) =
            FragmentSplines().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_BITMAP, bitmap)
                }
            }
    }
}
