package com.arvatel.photoeditor

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.arvatel.photoeditor.algorithms.Scaling
import kotlinx.android.synthetic.main.fragment_image_scaling.*
import kotlinx.android.synthetic.main.fragment_image_scaling.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ImageScalingFragment : Fragment() {

    lateinit var tempImage: Bitmap
    lateinit var progressObject: Progress

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_scaling, container, false)

        tempImage = (activity as ImageFromActivityInterface).getTempImage()

        view.showImageScaling.setImageBitmap(tempImage)
        progressObject = Progress(view.progressBarScalling)

        view.seekBarWidth.max = (activity as ImageFromActivityInterface).getMainImage().width
        view.seekBarHeight.max = (activity as ImageFromActivityInterface).getMainImage().height
        view.seekBarWidth.progress = view.seekBarWidth.max / 2
        view.seekBarHeight.progress = view.seekBarHeight.max / 2



        view.seekBarHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewLifecycleOwner.lifecycleScope.launch {
                    (activity as ImageFromActivityInterface).beforeLaoding(progressBarScalling)
                    val result = getScalledImage(view.seekBarWidth.progress, view.seekBarHeight.progress)
                    (activity as ImageFromActivityInterface).afterLaoding(progressBarScalling)
                    view.showImageScaling.setImageBitmap(result)
                }
            }
        })
        view.seekBarWidth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewLifecycleOwner.lifecycleScope.launch {
                    (activity as ImageFromActivityInterface).beforeLaoding(progressBarScalling)
                    val result = getScalledImage(view.seekBarWidth.progress, view.seekBarHeight.progress)
                    (activity as ImageFromActivityInterface).afterLaoding(progressBarScalling)
                    view.showImageScaling.setImageBitmap(result)
                }
            }
        })
        view.buttonApplyScaling.setOnClickListener {
            (activity as ImageFromActivityInterface).setTempImage(showImageScaling.drawable.toBitmap())

            Navigation.findNavController(view).navigate(R.id.action_imageScalingFragment_to_photoEditorFragment)
        }
        view.buttonCancelScaling.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_imageScalingFragment_to_photoEditorFragment)
        }
        return view
    }

    private suspend fun getScalledImage(width: Int, height: Int) =
        withContext(Dispatchers.Default) {
            Scaling.nearestNeighborScaling(
                tempImage,
                width, height,
                progressObject
            )
        }
}
