package com.fjlr.rickymortyapi.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.rickymortyapi.databinding.ActivityPersonajesBinding
import com.fjlr.rickymortyapi.model.adapter.PersonajeAdapter
import com.fjlr.rickymortyapi.model.api.EpisodeApi
import com.fjlr.rickymortyapi.model.api.PersonajeApi
import com.fjlr.rickymortyapi.model.data.Characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PersonajesActivity : AppCompatActivity() {

    //ATTRIBUTES
    private lateinit var binding: ActivityPersonajesBinding
    private lateinit var personajeAdapter: PersonajeAdapter
    private var personajes = mutableListOf<Characters>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //BINDING
        binding = ActivityPersonajesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        goToMain()
        initRecyclerView()
        loadCharacters()
    }

    /**
     * Initialize recyclerView
     * Collect the episode selected
     */
    private fun initRecyclerView() {
        personajeAdapter = PersonajeAdapter(personajes)

        binding.recyclerViewPersonaje.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPersonaje.adapter = personajeAdapter
    }


    /**
     * Toast for default
     */
    private fun defaultToast(text: String) {
        Toast.makeText(
            this@PersonajesActivity,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }


    /**
     * Build Retrofit with the URL RickYMorty
     */
    private fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun loadCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val personaje = getRetrofit().create(PersonajeApi::class.java).getPersonajes()



            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    defaultToast("Error al cargar las temporadas")
                }
            }
        }
    }

    /**
     * Go to Main Activity
     */
    private fun goToMain() {
        binding.btVolverPersonaje.setOnClickListener {
            finish()
        }
    }
}