package com.elano.pokemonsearch.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.elano.pokemonsearch.R
import com.elano.pokemonsearch.models.Pokemon
import com.squareup.picasso.Picasso
import org.apache.commons.lang3.StringUtils

class PokemonAdapter(private val context: Context, private val pokemons: ArrayList<Pokemon>?) : RecyclerView.Adapter<PokemonAdapter.MyViewHolder>() {

    override fun getItemCount(): Int = pokemons!!.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder =
            MyViewHolder(LayoutInflater.from(context).inflate(R.layout.pokemon_row, parent, false))

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        val pokemon = pokemons!![position]

        holder!!.tvPokemonName?.text = StringUtils.capitalize(pokemon.name)
        Picasso.with(context).load(pokemon.sprites.frontDefault).into(holder.ivPokemonImage)
    }

    fun add(pokemon: Pokemon?) {
        pokemons?.add(pokemon!!)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val ivPokemonImage = itemView?.findViewById<ImageView>(R.id.ivPokemonHolder)
        val tvPokemonName = itemView?.findViewById<TextView>(R.id.tvPokemonHolder)
    }
}