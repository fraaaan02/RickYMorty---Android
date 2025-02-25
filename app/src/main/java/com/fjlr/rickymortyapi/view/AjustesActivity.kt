package com.fjlr.rickymortyapi.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.rickymortyapi.databinding.ActivityAjustesBinding
import com.fjlr.rickymortyapi.model.preferences.UserApplication.Companion.preferencias

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


        goToMain()
        activacionSwich()

    }

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


    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        )
        delegate.applyDayNight()
    }


    private fun disbleDarkMode() {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
        delegate.applyDayNight()
    }


    /**
     * Go to Main Activity
     */
    private fun goToMain() {
        binding.btVolverAjustes.setOnClickListener {
            onBackPressed()
        }
    }

}