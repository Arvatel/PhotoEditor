package com.arvatel.photoeditor

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arvatel.photoeditor.algorithms.Filter.*
import kotlinx.android.synthetic.main.fragment_filter.view.*


class FilterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filter, container, false)
        var tempImage : Bitmap = (activity as ImageFromActivityInterface).getTempImage()
        var filter : Int = 0
        var contrastLevel : Float = view.seekBarContrast.progress.toFloat() - 500f

        view.showImageFilter.setImageBitmap((activity as ImageFromActivityInterface).getTempImage())

        view.buttonApplyFilter.setOnClickListener {
            (activity as ImageFromActivityInterface).setTempImage(tempImage)
            when(filter){
                1 -> (activity as ImageFromActivityInterface).setMainImage(
                    applySepiaFilter((activity as ImageFromActivityInterface).getMainImage()))
                2 -> (activity as ImageFromActivityInterface).setMainImage(
                    applyGreyFilter((activity as ImageFromActivityInterface).getMainImage()))
                3 -> (activity as ImageFromActivityInterface).setMainImage(
                    applySketchFilter((activity as ImageFromActivityInterface).getMainImage()))
                4 -> (activity as ImageFromActivityInterface).setMainImage(
                    applyCircularFilter((activity as ImageFromActivityInterface).getMainImage()))
                5 -> (activity as ImageFromActivityInterface).setMainImage(
                    applySharpining((activity as ImageFromActivityInterface).getMainImage()))
                6 -> (activity as ImageFromActivityInterface).setMainImage(
                    increaseContrast((activity as ImageFromActivityInterface).getMainImage(), contrastLevel))
            }
            Navigation.findNavController(view).navigate(R.id.action_filterFragment_to_photoEditorFragment)
        }

        view.buttonSepia.setOnClickListener {
            tempImage = applySepiaFilter((activity as ImageFromActivityInterface).getTempImage())
            filter = 1
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonGrey.setOnClickListener {
            tempImage = applyGreyFilter((activity as ImageFromActivityInterface).getTempImage())
            filter = 2
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonSketch.setOnClickListener {
            tempImage = applySketchFilter((activity as ImageFromActivityInterface).getTempImage())
            filter = 3
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonBonus.setOnClickListener {
            tempImage = applyCircularFilter((activity as ImageFromActivityInterface).getTempImage())
            filter = 4
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonUnsharp.setOnClickListener {
            tempImage = applySharpining((activity as ImageFromActivityInterface).getTempImage())
            filter = 5
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonContrast.setOnClickListener {
            contrastLevel = view.seekBarContrast.progress.toFloat() - 500f
            tempImage = increaseContrast((activity as ImageFromActivityInterface).getTempImage(), contrastLevel)
            filter = 6
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonCancelFilter.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_filterFragment_to_photoEditorFragment)
        }

        view.buttonClearFilter.setOnClickListener {
            tempImage = (activity as ImageFromActivityInterface).getTempImage()
            view.showImageFilter.setImageBitmap(tempImage)
        }

        return view
    }

}
