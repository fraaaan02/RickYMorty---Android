package com.fjlr.rickymortyapi.model.preferences

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class UserApplication : Application() {

    companion object {
        lateinit var preferencias: Preferencias
    }

    override fun onCreate() {
        super.onCreate()
        preferencias = Preferencias(applicationContext)

        if (preferencias.fetchOscureMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}