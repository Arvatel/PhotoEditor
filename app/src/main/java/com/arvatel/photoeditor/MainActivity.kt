package com.arvatel.photoeditor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startRotationFragment(view: View) {
        val fragmentRotation = FragmentRotation.newInstance("ddd","ssss");
        val fragmentActivity = supportFragmentManager
        val fragmentTransitionImpl = fragmentActivity.beginTransaction()
        fragmentTransitionImpl.add(R.id.fragment_container,fragmentRotation).addToBackStack(null).commit()
    }
}
