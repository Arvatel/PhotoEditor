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
import com.arvatel.photoeditor.algorithms.Filter.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.fragment_filter.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FilterFragment : Fragment() {
    var contrastLevel: Float = 0f
    lateinit var tempImage: Bitmap
    lateinit var progressObject: Progress

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myview = inflater.inflate(R.layout.fragment_filter, container, false)
        tempImage = (activity as ImageFromActivityInterface).getMainImage()
        contrastLevel = myview.seekBarContrast.progress.toFloat() - 500f
        progressObject = Progress(myview.progressBarContrast)

        myview.showImageFilter.setImageBitmap(tempImage)
        myview.seekBarContrast.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                contrastLevel = myview.seekBarContrast.progress.toFloat() - 500f
                startBackgroundThreadForFilter(CONTRAST)
            }
        })
        myview.buttonApplyFilter.setOnClickListener {
            (activity as ImageFromActivityInterface).setMainImage(myview.showImageFilter.drawable.toBitmap())
            Navigation.findNavController(myview).navigate(R.id.action_filterFragment_to_photoEditorFragment)
        }

        myview.buttonSepia.setOnClickListener {

            startBackgroundThreadForFilter(SPEIA)
        }

        myview.buttonGrey.setOnClickListener {
            startBackgroundThreadForFilter(GREY)
        }

        myview.buttonSketch.setOnClickListener {
            startBackgroundThreadForFilter(SKETCH)
        }

        myview.buttonBonus.setOnClickListener {
            startBackgroundThreadForFilter(CIRCULAR)
        }

        myview.buttonUnsharp.setOnClickListener {
            startBackgroundThreadForFilter(SHARPING)
        }

        myview.buttonCancelFilter.setOnClickListener {
            Navigation.findNavController(myview).navigate(R.id.action_filterFragment_to_photoEditorFragment)
        }

        myview.buttonClearFilter.setOnClickListener {
            myview.showImageFilter.setImageBitmap(tempImage)
        }

        return myview
    }

    fun startBackgroundThreadForFilter(filterCode: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            (activity as ImageFromActivityInterface).beforeLaoding(progressBarContrast)
            val result = applyFilter(filterCode)
            (activity as ImageFromActivityInterface).afterLaoding(progressBarContrast)
            showImageFilter.setImageBitmap(result)
        }
    }

    suspend fun applyFilter(filterCode: Int) = withContext(Dispatchers.Default) {
        when (filterCode) {
            SPEIA -> applySepiaFilter(tempImage, progressObject)

            GREY -> applyGreyFilter(tempImage, progressObject)

            SKETCH -> applySketchFilter(tempImage, progressObject)

            CIRCULAR -> applyCircularFilter(tempImage, progressObject)

            SHARPING -> applySharpining(tempImage, progressObject)
            else ->
                increaseContrast(
                    tempImage,
                    contrastLevel,
                    progressObject
                )
        }
    }


    companion object {
        const val SPEIA = 1
        const val GREY = 2
        const val SKETCH = 3
        const val CIRCULAR = 4
        const val SHARPING = 5
        const val CONTRAST = 6
    }
}
