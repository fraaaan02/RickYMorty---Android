package com.fjlr.rickymortyapi.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.rickymortyapi.R
import com.fjlr.rickymortyapi.databinding.ItemEpisodioBinding
import com.fjlr.rickymortyapi.model.data.Episode

class EpisodeAdapter(private var episodes: List<Episode>) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeAdapterViewHolder>() {

    inner class EpisodeAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemEpisodioBinding.bind(view)

        fun bind(episode: Episode) {
            binding.tvNombreEpisodio.text = episode.name
            binding.tvEpisodio.text = episode.episode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeAdapterViewHolder =
        EpisodeAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_episodio, parent, false)
        )

    override fun getItemCount(): Int = episodes.size

    override fun onBindViewHolder(holder: EpisodeAdapterViewHolder, position: Int) =
        holder.bind(episodes[position])
}