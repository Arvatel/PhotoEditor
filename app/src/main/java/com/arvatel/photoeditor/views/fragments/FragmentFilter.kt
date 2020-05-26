package com.arvatel.photoeditor.views.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.algorithms.Filter
import com.arvatel.photoeditor.views.MainActivity
import kotlinx.android.synthetic.main.fragment_filter.*


private const val PHOTO_BITMAP = "photoBitmap"



class FragmentFilter : Fragment() {
    lateinit var bitmap: Bitmap
    var revert = true;
    var filter: Bitmap?=null


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
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        filterFragmentImageView.setImageBitmap(bitmap)

        applyGrey.setOnClickListener{applyGreyFilter()}
        applySketch.setOnClickListener{applySketchFilter()}
        speia.setOnClickListener{applyspiaFilter()}
        applyBonus.setOnClickListener{applyCircularFilter()}
        unsharpMaskingBV.setOnClickListener{applyUnsharping()}
        filterFinishBV.setOnClickListener{finishFragment()}
        contrastBV.setOnClickListener { applyContrast() }
        filterDiscardBV.setOnClickListener {
            (activity as MainActivity).closeFragment(this)
        }

        filterFragmentImageView.setOnClickListener{
            if(filter!=null)
            {
                if(revert) {
                    filterFragmentImageView.setImageBitmap(bitmap)
                    revert = false;
                }
                else{
                    filterFragmentImageView.setImageBitmap(filter)
                    revert =true;
                }

            }
        }

    }

    private fun applyContrast() {
        val contrastLevel: Float
        if (contrastlevelET.text.isNotEmpty())
            contrastLevel = contrastlevelET.text.toString().toFloat()
        else
            contrastLevel = 120f
        filter = Filter.increaseContrast(bitmap, contrastLevel);
        filterFragmentImageView.setImageBitmap(filter)
    }


    private fun applyUnsharping() {
        filter = Filter.applySharpining(bitmap)
        filterFragmentImageView.setImageBitmap(filter)
    }
    private fun finishFragment() {
        (activity as MainActivity).closeFragment(this)
        (activity as MainActivity).showTheNewImage(filterFragmentImageView.drawable.toBitmap())
    }
    private fun applyCircularFilter(){
       filter = Filter.applyCircularFilter(bitmap)
        filterFragmentImageView.setImageBitmap(filter)
    }
    private fun applyspiaFilter() {
        filter = Filter.applySpeiaFilter(bitmap)
        filterFragmentImageView.setImageBitmap(filter)
    }

    fun applySketchFilter(){
        filter = Filter.applySketchFilter(bitmap)
        filterFragmentImageView.setImageBitmap(filter)
    }
    private fun applyGreyFilter() {
        filter = Filter.applyGreyFilter(bitmap)
        filterFragmentImageView.setImageBitmap(filter)
    }

    companion object {
        @JvmStatic
        fun newInstance(bitmap: Bitmap) =
            FragmentFilter().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_BITMAP, bitmap)
                }
            }
    }
}
