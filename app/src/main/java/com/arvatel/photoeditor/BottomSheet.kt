package com.arvatel.photoeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class BottomSheet: AppCompatActivity(), View.OnClickListener  {

    var buttonRotateBS: Button? = null
    var buttonResizeBS: Button? = null
    var buttonRetouchBS: Button? = null
    var buttonScalingBS: Button? = null
    var button4: Button? = null
    var button5: Button? = null

    var bottomSheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createBottomSheetDialog()
    }

    private fun createBottomSheetDialog() {
        if (bottomSheetDialog == null) {
            val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_bottom_sheet_tool, null)
            buttonRotateBS = view.findViewById(R.id.buttonRotateBS)
            buttonResizeBS = view.findViewById(R.id.buttonResizeBS)
            buttonRetouchBS = view.findViewById(R.id.buttonRetouchBS)
            buttonScalingBS = view.findViewById(R.id.buttonScalingBS)
            button4 = view.findViewById(R.id.button4)
            button5 = view.findViewById(R.id.button5)

            buttonRotateBS?.setOnClickListener(this)
            buttonResizeBS?.setOnClickListener(this)
            buttonRetouchBS?.setOnClickListener(this)
            buttonScalingBS?.setOnClickListener(this)
            button4?.setOnClickListener(this)
            button5?.setOnClickListener(this)

            bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog?.setContentView(view)
        }
    }

    fun showDialog(view: View?) {
        bottomSheetDialog?.show()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonRotateBS -> {
                bottomSheetDialog?.dismiss()
            }
            R.id.buttonResizeBS -> {
                bottomSheetDialog?.dismiss()
            }
            R.id.buttonRetouchBS -> {
                bottomSheetDialog?.dismiss()
            }
            R.id.buttonScalingBS -> {
                bottomSheetDialog?.dismiss()
            }
            R.id.button4 -> {
                bottomSheetDialog?.dismiss()
            }
            R.id.button5 -> {
                bottomSheetDialog?.dismiss()
            }
        }
    }
}