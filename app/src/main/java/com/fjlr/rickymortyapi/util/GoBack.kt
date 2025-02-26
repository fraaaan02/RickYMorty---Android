package com.fjlr.rickymortyapi.util

import android.app.Activity
import android.widget.Button

object GoBack {
    /**
     * Go to Main Activity
     */
    fun goToMain(activity: Activity, button: Button) {
        button.setOnClickListener {
            activity.finish()
        }
    }
}