package com.fjlr.rickymortyapi.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.rickymortyapi.databinding.ActivityPersonajesBinding
import com.fjlr.rickymortyapi.model.adapter.PersonajeAdapter
import com.fjlr.rickymortyapi.model.api.PersonajeApi
import com.fjlr.rickymortyapi.model.data.Characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        personajeAdapter = PersonajeAdapter(personajes) { onclickListener(it) }

        binding.recyclerViewPersonaje.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPersonaje.adapter = personajeAdapter
    }


    private fun onclickListener(personaje: Characters) {
        val intent = Intent(this, PersonajeDetalleActivity::class.java)
        intent.putExtra("ID", personaje.id)
        startActivity(intent)
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


    /**
     *
     */
    private fun loadCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val episode = getRetrofit()
                    .create(PersonajeApi::class.java)
                    .getPersonajeForId(getIdIntent())

                val personajeIds = episode.characters.map { url ->
                    url.split("/").last().toInt()
                }

                val deferredPersonajes = personajeIds.map { id ->
                    async(Dispatchers.IO) {
                        getRetrofit()
                            .create(PersonajeApi::class.java)
                            .getDetailPersonaje(id)
                    }
                }

                // Esperamos a que todas las llamadas finalicen y obtenemos la lista de personajes
                val personajesList = deferredPersonajes.awaitAll()

                withContext(Dispatchers.Main) {
                    binding.tvNombreEpisodio.text = episode.name
                    binding.tvEpisodio.text = episode.episode

                    personajes.clear()
                    personajes.addAll(personajesList)
                    personajeAdapter.notifyDataSetChanged()
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    defaultToast("Error al cargar los Personajes: ${e.message}")
                    Log.e("ERROR_LOAD_CHARACTERS", e.toString())
                }
            }
        }
    }


    private fun getIdIntent(): Int = intent.getIntExtra("ID", -1)


    /**
     * Go to Main Activity
     */
    private fun goToMain() {
        binding.btVolverPersonaje.setOnClickListener {
            finish()
        }
    }
}