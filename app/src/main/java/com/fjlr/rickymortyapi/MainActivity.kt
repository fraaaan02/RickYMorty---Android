package com.fjlr.rickymortyapi

import android.os.Bundle
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

    private fun initRecyclerView() {
        episodeAdapter = EpisodeAdapter(episodes)

        binding.recyclerViewEpisode.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewEpisode.adapter = episodeAdapter
    }

    private fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun buildTemporadas() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val temporada = getRetrofit().create(EpisodeApi::class.java).getTemporadas()

                if (temporada.results.isNotEmpty()) {
                    val e = temporada.results.size
                    val temporadaSize = (1..e).toList()

                    launch(Dispatchers.Main) {
                        val spinnerAdapter = ArrayAdapter(
                            this@MainActivity,
                            android.R.layout.simple_spinner_item,
                            temporadaSize
                        )
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerTemporada.adapter = spinnerAdapter
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error al cargar las temporadas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Error al cargar las temporadas",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}