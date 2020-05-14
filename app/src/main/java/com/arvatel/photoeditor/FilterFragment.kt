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
        var tempImage : Bitmap = (activity as ImageFromActivityInterface).getImage()

        view.showImageFilter.setImageBitmap((activity as ImageFromActivityInterface).getImage())

        view.buttonApplyFilter.setOnClickListener {
            (activity as ImageFromActivityInterface).setImage(tempImage)
            Navigation.findNavController(view).navigate(R.id.action_filterFragment_to_photoEditorFragment)
        }

        view.buttonCancelFilter.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_filterFragment_to_photoEditorFragment)
        }

        view.buttonSepia.setOnClickListener {
            tempImage = applySepiaFilter((activity as ImageFromActivityInterface).getImage())
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonGrey.setOnClickListener {
            tempImage = applyGreyFilter((activity as ImageFromActivityInterface).getImage())
            view.showImageFilter.setImageBitmap(tempImage)
        }

        view.buttonSketch.setOnClickListener {
            tempImage = applySketchFilter((activity as ImageFromActivityInterface).getImage())
            view.showImageFilter.setImageBitmap(tempImage)
        }

//        view.buttonBonus.setOnClickListener {
//            tempImage = applyCircularFilter((activity as ImageFromActivityInterface).getImage())
//            view.showImageFilter.setImageBitmap(tempImage)
//        }

        return view
    }

}
