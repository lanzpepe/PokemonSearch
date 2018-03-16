package com.elano.pokemonsearch.controllers

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.elano.pokemonsearch.R
import com.elano.pokemonsearch.adapters.PokemonAdapter
import com.elano.pokemonsearch.adapters.RecyclerItemClickListener
import com.elano.pokemonsearch.models.Pokemon
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), Callback, RecyclerItemClickListener.ClickListener {

    private val pokemonUrl = "https://pokeapi.co/api/v2/pokemon/"
    private var mPokemons: ArrayList<Pokemon>? = null
    private var mAdapter: PokemonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
        ibSearch.setOnClickListener { fetchJson() }
    }

    private fun fetchJson() {
        val name = etPokemonName.text.toString()

        if (TextUtils.isEmpty(name))
            toast("Please input search name.")
        else {
            val request = Request.Builder().url("$pokemonUrl${name.toLowerCase()}/").build()
            val client = OkHttpClient()

            progressBar.visibility = View.VISIBLE
            client.newCall(request).enqueue(this)
        }
    }

    @SuppressLint("NewApi")
    override fun onResponse(call: Call?, response: Response?) {
        val body = response?.body()?.string()
        val jsonObject = JSONObject(body)
        val pokemon = GsonBuilder().create().fromJson(body, Pokemon::class.java)

        mPokemons = ArrayList()
        mAdapter = PokemonAdapter(this, mPokemons)

        runOnUiThread {
            if (jsonObject.isNull("name"))
                toast("Pokemon not found.")
            else {
                recyclerView.adapter = mAdapter
                mAdapter?.add(pokemon)
                recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this, this))
            }
            progressBar.visibility = View.GONE
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        val pokemonFragment = PokemonFragment()
        val bundle = Bundle(); val pokemonPosition = mPokemons!![position]

        bundle.putSerializable(POKEMON, pokemonPosition)
        pokemonFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment, pokemonFragment).addToBackStack(TAG).commit()
        fragment.visibility = View.VISIBLE
    }

    @SuppressLint("PrivateResource")
    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0)
            fragmentManager.popBackStack()
        else
            super.onBackPressed()
    }

    override fun onFailure(call: Call?, e: IOException?) {
        Log.v(TAG, "Failed to fetch Pokemon data!")
    }

    private fun toast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "MainActivity"
        const val POKEMON = "key-pokemon"
    }
}
