package com.fjlr.rickymortyapi.model.preferences

import android.content.Context
import android.content.SharedPreferences
import com.fjlr.rickymortyapi.util.SHARE_FIELD_MODO_OSCURO
import com.fjlr.rickymortyapi.util.SHARE_PREFERENCES

/**
 * Preferences Class to save the state of the switch
 */
class Preferencias (content: Context) {

    private val preferences: SharedPreferences = content.getSharedPreferences(
        SHARE_PREFERENCES, Context.MODE_PRIVATE
    )

    /**
     * Save the state of the switch
     */
    fun saveOscureMode(isChecked: Boolean){
        preferences.edit()
            .putBoolean(SHARE_FIELD_MODO_OSCURO, isChecked)
            .apply()
    }

    /**
     * Fetch the state of the switch
     */
    fun fetchOscureMode() : Boolean = preferences.getBoolean(
        SHARE_FIELD_MODO_OSCURO, false
    )

}