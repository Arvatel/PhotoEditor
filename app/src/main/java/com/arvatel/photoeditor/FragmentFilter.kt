package com.arvatel.photoeditor

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_filter.*


private const val PHOTO_BITMAP = "photoBitmap"


/**
 * A simple [Fragment] subclass.
 * Use the [FragmentScall.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFilter : Fragment() {
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
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        filterFragmentImageView.setImageBitmap(bitmap)
        applyGrey.setOnClickListener{applyGreyFilter()}
        applySketch.setOnClickListener{applySketchFilter()}
        speia.setOnClickListener{applyspia()}
        applyBonus.setOnClickListener{applyCircularFilter()}
        //can be usued to extract thumbnail
//        val thumbImage = ThumbnailUtils.extractThumbnail(bitmap,320,320)
//        val bitmapwithGreyFilter = Filter.applyGreyFilter(thumbImage)
//        thumbnail.setImageBitmap(bitmapwithGreyFilter)


    }
    fun applyCircularFilter(){
        val bitmapwithFilter = Filter.applyCircularFilter(bitmap)
        filterFragmentImageView.setImageBitmap(bitmapwithFilter)
    }
    private fun applyspia() {
        val bitmapwithFilter = Filter.applySpeiaFilter(bitmap)
        filterFragmentImageView.setImageBitmap(bitmapwithFilter)
    }

    fun applySketchFilter(){
        val bitmapwithFilter = Filter.applySketchFilter(bitmap)
        filterFragmentImageView.setImageBitmap(bitmapwithFilter)
    }
    private fun applyGreyFilter() {
        val bitmapwithFilter = Filter.applyGreyFilter(bitmap)
        filterFragmentImageView.setImageBitmap(bitmapwithFilter)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param bitmap
         * @return A new instance of fragment FilterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(bitmap: Bitmap) =
            FragmentFilter().apply {
                arguments = Bundle().apply {
                    putParcelable(PHOTO_BITMAP, bitmap)
                }
            }
    }
}
