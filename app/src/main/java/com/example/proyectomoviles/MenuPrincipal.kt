package com.example.proyectomoviles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MenuPrincipal : AppCompatActivity() {
    private lateinit var dbHelper: BD_Peliculas
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeliculasAdapter
    private var peliculasList: MutableList<Pelicula> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        dbHelper = BD_Peliculas(this)
        // dbHelper.inicializarBaseDeDatos()
        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        adapter = PeliculasAdapter(peliculasList) { position ->
            dbHelper.eliminarPelicula(peliculasList[position].id)
            peliculasList.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Obtener y mostrar las películas
        val newData = dbHelper.obtenerPeliculas()
        adapter.updateData(newData)
        adapter.notifyDataSetChanged()
    }
}