package com.arvatel.photoeditor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_menu.view.*

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.buttonPhotoEditor.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_openPhotoFragment)
        }
        view.buttonSplineLines.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_splineFragment)
        }
        view.buttonCube.setOnClickListener {
            val text = "It looks much better in your imagination :)"
            val duration = Toast.LENGTH_LONG
            Toast.makeText(context, text, duration).show()
        }

        return view
    }

}
