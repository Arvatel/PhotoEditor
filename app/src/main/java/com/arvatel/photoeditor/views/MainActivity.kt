package com.arvatel.photoeditor.views

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
import com.arvatel.photoeditor.views.fragments.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotationBV.setOnClickListener { v -> startFragment(v) }
        filtersBV.setOnClickListener { v -> startFragment(v) }
        scalingBV.setOnClickListener { v -> startFragment(v) }
        opencvBV.setOnClickListener { v -> startFragment(v) }
        splinesBV.setOnClickListener { v -> startFragment(v) }
    }

    private fun startFragment(view: View) {

        view as Button
        //extract the photo from the image view to send it to the fragment
        imageView.invalidate()
        val bitmap = imageView.drawable.toBitmap()
        //get fragment object
        val fragment =
            when (view) {
                opencvBV -> FragmentOpenCv.newInstance(bitmap)
                filtersBV -> FragmentFilter.newInstance(bitmap)
                rotationBV -> FragmentRotation.newInstance(bitmap)
                splinesBV -> FragmentSplines.newInstance(bitmap)
                else -> FragmentScale.newInstance(bitmap)
            }

        val fragmentActivity = supportFragmentManager
        val fragmentTransitionImpl = fragmentActivity.beginTransaction()
        fragmentTransitionImpl.add(R.id.fragment_container, fragment).addToBackStack(null)
            .commit()
    }

    //called from the fragment to terminate itself
    fun closeFragment(fragment: Fragment) {
        // Get the FragmentManager.
        val fragmentManager: FragmentManager = supportFragmentManager
        // Check to see if the fragment is already showing.
        // Create and commit the transaction to remove the fragment.
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment).commit()
    }

    //called from the fragment to show the new results
    fun showTheNewImage(bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

}
