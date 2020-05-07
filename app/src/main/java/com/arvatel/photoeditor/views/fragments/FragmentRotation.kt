package com.arvatel.photoeditor.views.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.arvatel.photoeditor.views.MainActivity
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.algorithms.Rotate
import kotlinx.android.synthetic.main.fragment_rotation.*


private const val PHOTO_BITMAP = "photoBitmap"

class FragmentRotation : Fragment() {
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
        return inflater.inflate(R.layout.fragment_rotation, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //my code goes here
        fragmentPhoto.setImageBitmap(bitmap)
        //seekBar listener
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                    showProgress(progress.toString())

                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Do something
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // Do something
                }
            })
        //finish
        finishFragmentBV.setOnClickListener { finishFragment() }
        toTheLeftBV.setOnClickListener { toTheLeft() }
        discaredBV.setOnClickListener {
            (activity as MainActivity).closeFragment(this)
        }
    }

    private fun toTheLeft() {
            val rotatedBitmap: Bitmap = Rotate.rotate(bitmap)
            bitmap = rotatedBitmap
            fragmentPhoto.setImageBitmap(rotatedBitmap)

    }


    private fun finishFragment() {

        (activity as MainActivity).closeFragment(this)
        (activity as MainActivity).showTheNewImage(fragmentPhoto.drawable.toBitmap())
        //todo if the photo is reduced then i should make the changes to the original photo in asynic task
    }

    private fun showProgress(progress: String) {

        progressBarValue.text = progress
        val rotatedBitmap: Bitmap = Rotate.rotate(bitmap, progress.toDouble())

        fragmentPhoto.setImageBitmap(rotatedBitmap)
    }

    companion object {
        /**
         * @param bitmap
         * @return A new instance of fragment FragmentRotation.
         */
        @JvmStatic
        fun newInstance(bitmap: Bitmap) =
            FragmentRotation().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_BITMAP, bitmap)
                }
            }
    }
}
