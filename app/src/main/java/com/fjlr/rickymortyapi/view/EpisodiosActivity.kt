package com.fjlr.rickymortyapi.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fjlr.rickymortyapi.databinding.ActivityEpisodiosBinding
import com.fjlr.rickymortyapi.model.adapter.EpisodeAdapter
import com.fjlr.rickymortyapi.model.api.EpisodeApi
import com.fjlr.rickymortyapi.model.data.Episode
import com.fjlr.rickymortyapi.util.GoBack
import com.fjlr.rickymortyapi.util.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodiosActivity : AppCompatActivity() {

    //ATTRIBUTES
    private lateinit var binding: ActivityEpisodiosBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private var episodes = mutableListOf<Episode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //BINDING
        binding = ActivityEpisodiosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        GoBack.goToMain(this, binding.btVolverEpisodio)
        initRecyclerView()
        buildTemporadas()

    }

    /**
     * Initialize recyclerView
     * Collect the episode selected
     */
    private fun initRecyclerView() {
        episodeAdapter = EpisodeAdapter(episodes) { onclickListener(it) }

        binding.recyclerViewEpisode.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEpisode.adapter = episodeAdapter
    }

    /**
     * Collect the all episode E01 and add the list the spinner
     * Show the episodes of the first season (1 spinner position)
     */
    private fun buildTemporadas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val temporada =
                    RetrofitClient.instance.create(EpisodeApi::class.java).getTemporadas()
                withContext(Dispatchers.Main) {

                    if (temporada.results.isNotEmpty()) {

                        val spinnerAdapter = ArrayAdapter(
                            this@EpisodiosActivity,
                            android.R.layout.simple_spinner_item,
                            (1..temporada.results.size).toList()
                        )
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerTemporada.adapter = spinnerAdapter

                        loadEpisode()

                    } else {
                        launch(Dispatchers.Main) {
                            Toast.makeText(
                                this@EpisodiosActivity,
                                "Error al cargar las temporadas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Toast.makeText(
                        this@EpisodiosActivity,
                        "Error al cargar las temporadas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    /**
     * Collect the episode with the season selected (this format: S0X)
     * Add the episodes in the recyclerView, clear the episodes when change the season and notify
     */
    private fun buildEpisodes(temporada: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val temporadaFormat = "S%02d".format(temporada)

                val episodios = RetrofitClient.instance
                    .create(EpisodeApi::class.java)
                    .getEpisodesForSeason(temporadaFormat)

                if (episodios.results.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        episodes.clear()
                        episodes.addAll(episodios.results)
                        episodeAdapter.notifyDataSetChanged()
                    }
                } else {
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            this@EpisodiosActivity,
                            "No hay episodios en esta temporada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    Toast.makeText(
                        this@EpisodiosActivity,
                        "Error al cargar los episodios",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    /**
     * Load the episode from season selected
     */
    private fun loadEpisode() {
        binding.spinnerTemporada.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    buildEpisodes(position + 1)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }


    /**
     * Go to detailed activity and send the ID the episode selected
     */
    private fun onclickListener(episode: Episode) {
        val intent = Intent(this, PersonajesActivity::class.java)
        intent.putExtra("ID", episode.id)
        startActivity(intent)
    }

}