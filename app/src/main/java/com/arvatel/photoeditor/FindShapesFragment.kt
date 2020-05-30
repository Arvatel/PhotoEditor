package com.arvatel.photoeditor

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.arvatel.photoeditor.algorithms.OpenCvUtil
import kotlinx.android.synthetic.main.fragment_find_shapes.*
import kotlinx.android.synthetic.main.fragment_find_shapes.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FindShapesFragment : Fragment() {
    lateinit var tempImage: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_shapes, container, false)
        tempImage = (activity as ImageFromActivityInterface).getMainImage()
        var foundFaces = false
        var foundShapes = false

        view.showImageFindShapes.setImageBitmap(tempImage)

        view.buttonApplyFindShapes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_findShapesFragment_to_photoEditorFragment)
        }
        view.buttonCancelFindShapes.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_findShapesFragment_to_photoEditorFragment)
        }

        view.buttonFindFaces.setOnClickListener {
            val con = context
            if (!foundFaces) {
                startBackgroundThread(FACE)
                foundFaces = true
            }
        }
        view.buttonFindBasicShapes.setOnClickListener {
            if (!foundShapes) {
                startBackgroundThread(SHAPE)
                foundShapes = true
            }
        }
        view.buttonCleanShapes.setOnClickListener {
            view.showImageFindShapes.setImageBitmap(tempImage)
            foundFaces = false
            foundShapes = false
        }

        return view
    }

    fun startBackgroundThread(findWhat: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            (activity as ImageFromActivityInterface).beforeLaoding(progressBarOpenCV)
            val result = searchObjects(findWhat)
            (activity as ImageFromActivityInterface).afterLaoding(progressBarOpenCV)
            showImageFindShapes.setImageBitmap(result)
        }
    }

    private suspend fun searchObjects(findWhat: Int) = withContext(Dispatchers.Default) {
        when (findWhat) {
            SHAPE -> OpenCvUtil.searchForShapes(
                tempImage.copy(
                    Bitmap.Config.ARGB_8888,
                    true
                )
            )
            else -> OpenCvUtil.searchForFaces(
                tempImage.copy(Bitmap.Config.ARGB_8888, true),
                context
            )
        }
    }

    companion object {
        const val FACE = 1
        const val SHAPE = 2
    }
}
