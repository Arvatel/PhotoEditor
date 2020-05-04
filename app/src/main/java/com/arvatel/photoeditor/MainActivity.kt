package com.arvatel.photoeditor


import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rotation.setOnClickListener { startRotationFragment() }
        filters.setOnClickListener { startFilterFragment() }
        scalingBV.setOnClickListener { startScallFragment() }
    }

    //todo clean the code in the two methods bellow
    private fun startFilterFragment() {
        imageView.invalidate()
        val bitmap = imageView.getDrawable().toBitmap()
        //todo check bitmap sizes if it's big then reduce it to show changes to user in real time
        //and prevent program from crashing
        val filterFragment = FragmentFilter.newInstance(bitmap);
        val fragmentActivity = supportFragmentManager
        val fragmentTransitionImpl = fragmentActivity.beginTransaction()
        fragmentTransitionImpl.add(R.id.fragment_container, filterFragment).addToBackStack(null)
            .commit()

    }

    private fun startScallFragment() {
        imageView.invalidate()
        val bitmap = imageView.getDrawable().toBitmap()
        //todo check bitmap sizes if it's big then reduce it to show changes to user in real time
        //and prevent program from crashing
        val scallFragment = FragmentScall.newInstance(bitmap);
        val fragmentActivity = supportFragmentManager
        val fragmentTransitionImpl = fragmentActivity.beginTransaction()
        fragmentTransitionImpl.add(R.id.fragment_container, scallFragment).addToBackStack(null)
            .commit()
    }

    fun startRotationFragment() {
        imageView.invalidate()
        val bitmap = imageView.getDrawable().toBitmap()
        //todo check bitmap sizes if it's big then reduce it to show changes to user in real time
        //and prevent program from crashing
        val fragmentRotation = FragmentRotation.newInstance(bitmap);
        val fragmentActivity = supportFragmentManager
        val fragmentTransitionImpl = fragmentActivity.beginTransaction()
        fragmentTransitionImpl.add(R.id.fragment_container,fragmentRotation).addToBackStack(null).commit()
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
