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
import com.arvatel.photoeditor.algorithms.Rotate
import kotlinx.android.synthetic.main.fragment_image_rotation.*
import kotlinx.android.synthetic.main.fragment_image_rotation.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageRotationFragment : Fragment() {
    lateinit var tempImage: Bitmap
    lateinit var thumbnail: Bitmap
    lateinit var myview: View
    lateinit var progressObject: Progress
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        myview = inflater
            .inflate(R.layout.fragment_image_rotation, container, false)

        progressObject = Progress(myview.progressBarRotation)

        myview.showImageRotate.setImageBitmap((activity as ImageFromActivityInterface).getMainImage())

        tempImage = (activity as ImageFromActivityInterface).getMainImage()
        thumbnail = (activity as ImageFromActivityInterface).getThumbnail()


        myview.buttonCancel.setOnClickListener {
            Navigation.findNavController(myview)
                .navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }

        myview.buttonApply.setOnClickListener {
            (activity as ImageFromActivityInterface).setThumbnail(
                Rotate.rotate(
                    thumbnail, inRadian(myview.rotationBar.progress),
                    progressObject,
                    false
                )
            )
            (activity as ImageFromActivityInterface).setMainImage(myview.showImageRotate.drawable.toBitmap())

            Navigation.findNavController(myview)
                .navigate(R.id.action_imageRotationFragment_to_photoEditorFragment)
        }

        myview.rotationBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                myview.showImageRotate.setImageBitmap(
                    Rotate.rotate(thumbnail, inRadian(progress), progressObject, false)
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewLifecycleOwner.lifecycleScope.launch {
                    (activity as ImageFromActivityInterface).beforeLaoding(progressBarRotation)
                    val result = withContext(Dispatchers.Default) {
                        Rotate.rotate(
                            tempImage,
                            inRadian(myview.rotationBar.progress),
                            progressObject,
                            true
                        )
                    }
                    (activity as ImageFromActivityInterface).afterLaoding(progressBarRotation)
                    myview.showImageRotate.setImageBitmap(result)
                }
            }
        })
        myview.buttonRotate.setOnClickListener {

            myview.rotationBar.progress = 45

            //we know this is an overhead on cpu but, this is our first time with multithreading^^
            viewLifecycleOwner.lifecycleScope.launch {
                thumbnail = withContext(Dispatchers.Default) {
                    Rotate.rotate(thumbnail, progressObject, false)
                }

            }
            viewLifecycleOwner.lifecycleScope.launch {
                (activity as ImageFromActivityInterface).beforeLaoding(progressBarRotation)
                tempImage = doe()//todo clean this code now it's working normal with a function
                (activity as ImageFromActivityInterface).afterLaoding(progressBarRotation)
                myview.showImageRotate.setImageBitmap(tempImage)
            }

        }

        return myview
    }
    suspend fun doe()=withContext(Dispatchers.Default) {
        Rotate.rotate(
            tempImage,
            progressObject,
            true
        )
    }
    fun inRadian(progress: Int) = Math.toRadians((progress - 45).toDouble())
}
