package com.fjlr.rickymortyapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.rickymortyapi.databinding.ActivityMainBinding
import com.fjlr.rickymortyapi.view.AjustesActivity
import com.fjlr.rickymortyapi.view.EpisodiosActivity

/**
 * MAIN CLASS
 * @author Francisco Joaquin Lopez Ros
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //BINDING
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btTemporadas.setOnClickListener {
            startActivity(Intent(this, EpisodiosActivity::class.java))
        }

        binding.btAjustes.setOnClickListener {
            startActivity(Intent(this, AjustesActivity::class.java))
        }
    }

}