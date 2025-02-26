package com.fjlr.rickymortyapi.model.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fjlr.rickymortyapi.R
import com.fjlr.rickymortyapi.databinding.ItemPersonajeBinding
import com.fjlr.rickymortyapi.model.data.Characters
import com.squareup.picasso.Picasso

class PersonajeAdapter (private var personaje: List<Characters>, private val fn: (Characters) ->  Unit):
RecyclerView.Adapter<PersonajeAdapter.PersonajeAdapterViewHolder>(){

    inner class PersonajeAdapterViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemPersonajeBinding.bind(view)

        fun bind(personaje: Characters){
            binding.tvNombre.text = personaje.name
            binding.tvEstado.text = personaje.status
            binding.tvLugar.text = personaje.location.name
            binding.tvUrl.text = personaje.created
            Picasso.get().load(personaje.image).into(binding.ivImagen)

            //Configuration the listener of clicks in the list of the element
            itemView.setOnClickListener{fn(personaje)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonajeAdapterViewHolder =
        PersonajeAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_personaje, parent, false)
        )

    override fun onBindViewHolder(holder: PersonajeAdapterViewHolder, position: Int) =
        holder.bind(personaje[position])

    override fun getItemCount(): Int = personaje.size
}