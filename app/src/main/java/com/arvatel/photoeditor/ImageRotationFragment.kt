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

private class SeekBarListener(val activity : MainActivity, val view: View) : SeekBar.OnSeekBarChangeListener {

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        var tempImage : Bitmap = Rotate.rotate(activity.currentImage, Math.toRadians((progress - 180).toDouble()))
        view.rotationPhoto.setImageBitmap(tempImage)
    }
    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}

class ImageRotationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity : MainActivity = requireActivity() as MainActivity
        val view = inflater.inflate(R.layout.fragment_image_rotation, container, false)

        view.buttonCancel.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }
        view.buttonApply.setOnClickListener{
            activity.currentImage = Rotate.rotate(activity.currentImage, Math.toRadians((view.rotationBar.progress - 180).toDouble()))
            Navigation.findNavController(view).navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }
        view.rotationBar.setOnSeekBarChangeListener(SeekBarListener(activity, view))


        return view
    }


}
