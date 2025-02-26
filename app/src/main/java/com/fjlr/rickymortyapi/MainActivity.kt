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


        setImageForMode(findViewById(R.id.fotoMorty), R.drawable.morty, R.drawable.mortynight)
        setImageForMode(findViewById(R.id.fotoRick), R.drawable.rick, R.drawable.ricknight)
        setImageForMode(findViewById(R.id.fotoSummer), R.drawable.summer, R.drawable.summernight)
        setImageForMode(findViewById(R.id.fotoBeth), R.drawable.beth, R.drawable.bethnight)
        setImageForMode(findViewById(R.id.fotoJerry), R.drawable.jerry, R.drawable.jerrynight)
    }

    /**
     * Set image for mode (light or dark)
     */
    private fun setImageForMode(imageView: ImageView, lightImage: Int, darkImage: Int) {
        val modoOscuro = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val selectedImage =
            if (modoOscuro == Configuration.UI_MODE_NIGHT_YES) darkImage else lightImage
        imageView.setImageResource(selectedImage)
    }


}