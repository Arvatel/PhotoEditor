package com.arvatel.photoeditor.views

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.arvatel.photoeditor.R
import com.arvatel.photoeditor.views.fragments.FragmentFilter
import com.arvatel.photoeditor.views.fragments.FragmentOpenCv
import com.arvatel.photoeditor.views.fragments.FragmentRotation
import com.arvatel.photoeditor.views.fragments.FragmentScall
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotationBV.setOnClickListener { v -> startFragment(v) }
        filtersBV.setOnClickListener { v -> startFragment(v) }
        scalingBV.setOnClickListener { v -> startFragment(v) }
        opencvBV.setOnClickListener { v -> startFragment(v) }
    }

    private fun startFragment(view: View) {
        view as Button
        imageView.invalidate()
        val bitmap = imageView.getDrawable().toBitmap()

        val fragment =
            when (view) {
                opencvBV -> FragmentOpenCv.newInstance(bitmap)
                filtersBV -> FragmentFilter.newInstance(bitmap)
                rotationBV -> FragmentRotation.newInstance(bitmap)
                else -> FragmentScall.newInstance(bitmap)
            }

        val fragmentActivity = supportFragmentManager
        val fragmentTransitionImpl = fragmentActivity.beginTransaction()
        fragmentTransitionImpl.add(R.id.fragment_container, fragment).addToBackStack(null)
            .commit()
    }

    fun closeFragment(fragment: Fragment) {
        // Get the FragmentManager.
        val fragmentManager: FragmentManager = supportFragmentManager
        // Check to see if the fragment is already showing.
        // Create and commit the transaction to remove the fragment.
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment).commit()
    }

    fun showTheNewImage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

}
