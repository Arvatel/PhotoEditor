package com.arvatel.photoeditor

import android.graphics.Bitmap
import android.opengl.ETC1.getWidth
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.Navigation
import com.arvatel.photoeditor.algorithms.Scaling
import kotlinx.android.synthetic.main.fragment_image_rotation.view.*
import kotlinx.android.synthetic.main.fragment_image_scaling.*
import kotlinx.android.synthetic.main.fragment_image_scaling.view.*
import kotlinx.android.synthetic.main.fragment_image_scaling.view.seekBarHeight


class ImageScalingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_scaling, container, false)


        view.showImageScaling.setImageBitmap((activity as ImageFromActivityInterface).getImage())

        view.seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var tempImage : Bitmap = Scaling.nearestNeighborScaling((activity as ImageFromActivityInterface).getImage(),
                seekBarWidth.progress, seekBarHeight.progress)

                view.showImageScaling.setImageBitmap(tempImage)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        view.seekBarWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var tempImage : Bitmap = Scaling.nearestNeighborScaling((activity as ImageFromActivityInterface).getImage(),
                    seekBarWidth.progress, seekBarHeight.progress)

                view.showImageScaling.setImageBitmap(tempImage)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        view.buttonApplyScaling.setOnClickListener {
            (activity as ImageFromActivityInterface).setImage(Scaling.nearestNeighborScaling((activity as ImageFromActivityInterface).getImage(),
                seekBarWidth.progress, seekBarHeight.progress))
            Navigation.findNavController(view).navigate(R.id.action_imageScalingFragment_to_photoEditorFragment)
        }
        view.buttonCancelScaling.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_imageScalingFragment_to_photoEditorFragment)
        }
        return view
    }
}
