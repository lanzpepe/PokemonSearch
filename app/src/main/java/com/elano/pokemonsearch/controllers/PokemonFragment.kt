package com.elano.pokemonsearch.controllers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elano.pokemonsearch.R
import com.elano.pokemonsearch.models.Pokemon
import com.squareup.picasso.Picasso
import org.apache.commons.lang3.StringUtils
import kotlinx.android.synthetic.main.fragment_pokemon.view.*
import java.util.*

class PokemonFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_pokemon, container, false)
        val pokemon = arguments.getSerializable(MainActivity.POKEMON) as Pokemon

        setViews(rootView, pokemon)

        return rootView
    }

    private fun setViews(view: View?, pokemon: Pokemon?) {
        var separatorA: String; var separatorT: String

        Picasso.with(context).load(pokemon!!.sprites.frontDefault).into(view!!.ivPokemon)
        view.tvPokemonName?.append(" ${StringUtils.capitalize(pokemon.name)}")
        view.tvPokemonId?.append(" ${pokemon.id}")
        view.tvPokemonWeight?.append(" %.1f kg".format(Locale.US, pokemon.weight.toDouble() / 10))
        view.tvPokemonHeight?.append(" %.2f m".format(Locale.US, pokemon.height.toDouble() / 10))
        for (i in 0 until pokemon.abilities.size) {
            separatorA = if (i < pokemon.abilities.size - 1) getString(R.string.text_bullet) else ""
            view.tvPokemonAbilities?.append(" ${StringUtils.capitalize(pokemon.abilities[i].ability.name)} $separatorA")
        }
        for (i in 0 until pokemon.types.size) {
            separatorT = if (i < pokemon.types.size - 1) getString(R.string.text_bullet) else ""
            view.tvPokemonTypes?.append(" ${StringUtils.capitalize(pokemon.types[i].type.name)} $separatorT")
        }
        for (i in 0 until pokemon.stats.size) {
            when (i) {
                0 -> view.tvPokemonSpeed.append(" ${pokemon.stats[i].baseStat}")
                1 -> view.tvPokemonSpecialDefense.append(" ${pokemon.stats[i].baseStat}")
                2 -> view.tvPokemonSpecialAttack.append(" ${pokemon.stats[i].baseStat}")
                3 -> view.tvPokemonDefense.append(" ${pokemon.stats[i].baseStat}")
                4 -> view.tvPokemonAttack.append(" ${pokemon.stats[i].baseStat}")
                5 -> view.tvPokemonHp.append(" ${pokemon.stats[i].baseStat}")
            }
        }
        view.btnClose.setOnClickListener {
            activity.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).clearBackStackInclusive(MainActivity.TAG)
    }

}// Required empty public constructor
