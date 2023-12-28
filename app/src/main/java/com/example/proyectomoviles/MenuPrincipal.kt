package com.example.proyectomoviles

import android.annotation.SuppressLint
import com.example.proyectomoviles.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout


class MenuPrincipal : AppCompatActivity() {
    private lateinit var dbHelper: BD_Peliculas

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        dbHelper = BD_Peliculas(this)

        if (!dbHelper.isBaseDeDatosInicializada()) {
            dbHelper.inicializarBaseDeDatos()
        }
        // Obtén la lista de películas desde la base de datos
        val peliculas = dbHelper.obtenerPeliculas()
        // Llama a la función para mostrar las películas en el GridLayout
        mostrarPeliculasEnGridLayout(peliculas)

    }

    @SuppressLint("MissingInflatedId")
    private fun mostrarPeliculasEnGridLayout(peliculas: List<Pelicula>) {
        val gridLayout = findViewById<GridLayout>(R.id.gridLayoutPeliculas)

        for (i in 0 until peliculas.size) {
            // Obtener la película correspondiente desde la lista
                val pelicula = peliculas[i]

                // Inflar la vista de item_pelicula
                val itemView = LayoutInflater.from(this).inflate(R.layout.item_pelicula, gridLayout, false)

                // Acceder a las vistas dentro de item_pelicula
                val imgPelicula = itemView.findViewById<ImageView>(R.id.imgPelicula)
                val txtNombrePelicula = itemView.findViewById<TextView>(R.id.txtNombrePelicula)
                val txtDescripcion = itemView.findViewById<TextView>(R.id.txtDescripcion)

                // Configurar la imagen y el texto para cada película
                imgPelicula.setImageResource(pelicula.imagen)
                txtNombrePelicula.text = pelicula.nombre
                txtDescripcion.text = pelicula.descripcion

                // Agregar el elemento al GridLayout
                gridLayout.addView(itemView)

                // Puedes agregar un listener para manejar eventos de clic en cada elemento
                itemView.setOnClickListener {
                    // Implementa el código para manejar el clic en la película
                    // Por ejemplo, puedes abrir una nueva actividad para mostrar detalles
                    // de la película.
                    Log.d("MenuPrincipal", "Pelicula clickeada: ${pelicula.nombre}")
                }
        }
    }
}