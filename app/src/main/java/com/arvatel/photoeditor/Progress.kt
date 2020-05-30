package com.arvatel.photoeditor

import android.widget.ProgressBar

class Progress(val progressBar: ProgressBar) {
    // Reports a progress between 0 and 1
    fun report(value: Double){
        progressBar.setProgress((value * 100).toInt(), true);
    }
}