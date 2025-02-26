package com.fjlr.rickymortyapi.view

import android.content.Intent
import android.os.Bundle
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
import com.fjlr.rickymortyapi.util.GoBack
import com.fjlr.rickymortyapi.util.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Characters Activity
 */
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

        GoBack.goToMain(this, binding.btVolverPersonaje)
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


    /**
     * Go to detailed activity and send the ID the character selected
     */
    private fun onclickListener(personaje: Characters) {
        val intent = Intent(this, PersonajeDetalleActivity::class.java)
        intent.putExtra("ID", personaje.id)
        startActivity(intent)
    }

    /**
     * Add the list to the recyclerview and the episode data
     *
     * Collect the episode with the ID
     * Catch the ID for characters
     * Makes requests to the API in parallel, wait and return
     */
    private fun loadCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val intent: Int = intent.getIntExtra("ID", -1)

                val episode = RetrofitClient.instance
                    .create(PersonajeApi::class.java)
                    .getPersonajeForId(intent)

                val personajeIds = episode.characters.map { url ->
                    url.split("/").last().toInt()
                }

                val deferredPersonajes = personajeIds.map { id ->
                    async(Dispatchers.IO) {
                        RetrofitClient.instance
                            .create(PersonajeApi::class.java)
                            .getDetailPersonaje(id)
                    }
                }

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
                    Toast.makeText(
                        this@PersonajesActivity,
                        "Error al cargar los personajes",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}