package com.fjlr.rickymortyapi.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.rickymortyapi.databinding.ActivityAjustesBinding
import com.fjlr.rickymortyapi.model.preferences.UserApplication.Companion.preferencias
import com.fjlr.rickymortyapi.util.GoBack

/**
 * Ajustes Class
 */
class AjustesActivity : AppCompatActivity() {

    //ATTRIBUTES
    private lateinit var binding: ActivityAjustesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //BINDING
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        isSave()
        GoBack.goToMain(this, binding.btVolverAjustes)
        activacionSwich()

    }

    /**
     * Check if the switch is enabled or not and update the icon
     */
    private fun isSave() {
        binding.smModoOscuro.isChecked = preferencias.fetchOscureMode()
    }


    /**
     * Is activated the switch to enable dark mode or not
     */
    private fun activacionSwich() {
        binding.smModoOscuro.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableDarkMode()
                preferencias.saveOscureMode(true)
            } else {
                disbleDarkMode()
                preferencias.saveOscureMode(false)
            }
        }
    }


    /**
     * Enable Dark Mode
     */
    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        )
        delegate.applyDayNight()
    }


    /**
     * Disable Dark Mode
     */
    private fun disbleDarkMode() {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
        delegate.applyDayNight()
    }

}