package com.fjlr.rickymortyapi.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fjlr.rickymortyapi.databinding.ActivityPersonajeDetalleBinding
import com.fjlr.rickymortyapi.model.api.PersonajeApi
import com.fjlr.rickymortyapi.util.GoBack
import com.fjlr.rickymortyapi.util.RetrofitClient
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonajeDetalleActivity : AppCompatActivity() {

    //ATTRIBUTES
    private lateinit var binding: ActivityPersonajeDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //BINDING
        binding = ActivityPersonajeDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        GoBack.goToMain(this, binding.btVolverDetalle)

        val idPersonaje = intent.getIntExtra("ID", -1)
        if (idPersonaje != -1) {
            loadPersonaje(idPersonaje)
        }

    }

    /**
     * Collect the character of id and show the details
     */
    private fun loadPersonaje(idPersonaje: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val personaje = RetrofitClient.instance
                    .create(PersonajeApi::class.java)
                    .getDetailPersonaje(idPersonaje)

                withContext(Dispatchers.Main) {
                    binding.tvPersonajeName.text = personaje.name
                    binding.tvPersonajeEstado.text = personaje.status
                    binding.tvPersonajeLugar.text = personaje.location.name
                    Picasso.get().load(personaje.image).into(binding.ivImagenPersonaje)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@PersonajeDetalleActivity,
                        "Error al cargar personaje",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}