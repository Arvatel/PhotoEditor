package com.arvatel.photoeditor

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.Navigation
import com.arvatel.photoeditor.algorithms.Rotate
import kotlinx.android.synthetic.main.fragment_image_rotation.view.*


class ImageRotationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_rotation, container, false)

        view.showImageRotate.setImageBitmap((activity as ImageFromActivityInterface).getImage())

        view.buttonCancel.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }

        view.buttonApply.setOnClickListener{
            (activity as ImageFromActivityInterface).setImage(
                Rotate.rotate((activity as ImageFromActivityInterface).getImage(), Math.toRadians((view.rotationBar.progress - 45).toDouble())))

            Navigation.findNavController(view).navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }

        view.rotationBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var tempImage : Bitmap = Rotate.rotate((activity as ImageFromActivityInterface).getImage(),
                    Math.toRadians((progress - 45).toDouble()))

                view.showImageRotate.setImageBitmap(tempImage)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

//        view.buttonRotate.setOnClickListener {
//            val tempImage : Bitmap = Rotate.rotate((activity as ImageFromActivityInterface).getImage())
//        }
        return view
    }
}
