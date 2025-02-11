package com.arvatel.photoeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_spline.view.*

class SplineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_spline, container, false)

        view.buttonCancelSpline.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splineFragment_to_menuFragment)
        }

        view.buttonApplySpline.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splineFragment_to_menuFragment)
        }

        view.buttonLineSpline.setOnClickListener {
            view.splinesIV.callOnDraw(false, true)
        }

        view.buttonCubicSpline.setOnClickListener {
            view.splinesIV.callOnDraw(true, false)
        }

        view.buttonCleanSpline.setOnClickListener {
            view.splinesIV.clean()
        }

        return view
    }

}
