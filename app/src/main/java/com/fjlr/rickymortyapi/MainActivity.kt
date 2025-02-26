package com.fjlr.rickymortyapi

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
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

    //ATTRIBUTES
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

        //Go to Episodes Activity
        binding.btTemporadas.setOnClickListener {
            startActivity(Intent(this, EpisodiosActivity::class.java))
        }

        //Go to Ajustes Activity
        binding.btAjustes.setOnClickListener {
            startActivity(Intent(this, AjustesActivity::class.java))
        }

        imageNight()

    }

    private fun imageNight(){
        val imageView: ImageView = binding.fotoBeth
        val modoOscuro = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (modoOscuro == Configuration.UI_MODE_NIGHT_YES) {
            imageView.setImageResource(R.drawable.bethnight) // Imagen con mejor contraste
        } else {
            imageView.setImageResource(R.drawable.beth) // Imagen normal
        }

    }

}