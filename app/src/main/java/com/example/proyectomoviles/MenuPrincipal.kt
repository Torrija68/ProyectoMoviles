package com.example.proyectomoviles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
        adapter = PeliculasAdapter(peliculasList,
            onDeleteClickListener = { position ->
                dbHelper.eliminarPelicula(peliculasList[position].id)
                peliculasList.removeAt(position)
                adapter.notifyDataSetChanged()
            },
            onEditClickListener = { position ->
                abrirActividadEditar(peliculasList[position])
            }
        )
        recyclerView.adapter = adapter

        val btnAgregarPelicula = findViewById<Button>(R.id.btnAgregarPelicula)
        btnAgregarPelicula.setOnClickListener() {
            abrirActividadCrear()
        }
    }

    override fun onResume() {
        super.onResume()
        // Obtener y mostrar las películas
        val newData = dbHelper.obtenerPeliculas()
        adapter.updateData(newData)
        adapter.notifyDataSetChanged()
    }
    private fun abrirActividadEditar(pelicula: Pelicula) {
        val intent = Intent(this, ModificarPelicula::class.java)
        intent.putExtra("pelicula_id", pelicula.id)
        intent.putExtra("pelicula_nombre", pelicula.nombre)
        intent.putExtra("pelicula_descripcion", pelicula.descripcion)
        intent.putExtra("pelicula_imagen", pelicula.imagen)
        startActivity(intent)
    }
    private fun abrirActividadCrear() {
        // Aquí debes abrir la actividad para crear una nueva película
        val intent = Intent(this, CrearPelicula::class.java)
        startActivity(intent)
    }
}