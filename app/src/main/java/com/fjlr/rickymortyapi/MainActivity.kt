package com.fjlr.rickymortyapi

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
import com.fjlr.rickymortyapi.databinding.ActivityMainBinding
import com.fjlr.rickymortyapi.model.adapter.EpisodeAdapter
import com.fjlr.rickymortyapi.model.api.EpisodeApi
import com.fjlr.rickymortyapi.model.data.Episode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var episodeAdapter: EpisodeAdapter
    private var episodes = mutableListOf<Episode>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        buildTemporadas()


    }


    /**
     * Initialize recyclerView
     */
    private fun initRecyclerView() {
        episodeAdapter = EpisodeAdapter(episodes)

        binding.recyclerViewEpisode.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEpisode.adapter = episodeAdapter
    }


    /**
     * Toast for default
     */
    private fun defaultToast(text: String) {
        Toast.makeText(
            this@MainActivity,
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
     * Collect the all episode E01 and add the list the spinner
     * Show the episodes of the first season (1 spinner position)
     */
    private fun buildTemporadas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val temporada = getRetrofit().create(EpisodeApi::class.java).getTemporadas()

                if (temporada.results.isNotEmpty()) {

                    launch(Dispatchers.Main) {
                        val spinnerAdapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_spinner_item,
                            (1..temporada.results.size).toList()
                        )
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerTemporada.adapter = spinnerAdapter

                        loadEpisode()

                    }
                } else {
                    defaultToast("Error al cargar las temporadas")
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    defaultToast("Error al cargar las temporadas")
                }
            }
        }
    }


    /**
     *
     */
    private fun buildEpisodes(temporada: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val temporadaFormat = "S%02d".format(temporada)

                val episodios = getRetrofit()
                    .create(EpisodeApi::class.java)
                    .getEpisodesForSeason(temporadaFormat)

                if (episodios.results.isNotEmpty()) {
                    launch(Dispatchers.Main) {
                        episodes.clear()
                        episodes.addAll(episodios.results)
                        episodeAdapter.notifyDataSetChanged()
                    }
                } else {
                    launch(Dispatchers.Main) { defaultToast("No hay episodios en esta temporada") }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) { defaultToast("Error al cargar los episodios") }
            }
        }
    }


    /**
     *
     */
    private fun loadEpisode() {
        binding.spinnerTemporada.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                buildEpisodes(position + 1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

}