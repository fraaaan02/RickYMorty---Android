package com.fjlr.rickymortyapi.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.rickymortyapi.R
import com.fjlr.rickymortyapi.databinding.ItemEpisodioBinding
import com.fjlr.rickymortyapi.model.data.Episode

/**
 * Adapter Class to show the list of episodes in the recyclerview
 * Capture clicks the every item in the list
 */
class EpisodeAdapter(private var episodes: List<Episode>, private val fn: (Episode) ->  Unit) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeAdapterViewHolder>() {

    /**
     * ViewHolder for episode list items.
     */
    inner class EpisodeAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemEpisodioBinding.bind(view)

        /**
         * Bind the data from episodes the ViewHolder view
         */
        fun bind(episode: Episode) {
            binding.tvNombreEpisodio.text = episode.name
            binding.tvEpisodio.text = episode.episode

            //Configuration the listener of clicks in the list of the element
            itemView.setOnClickListener{fn(episode)}
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