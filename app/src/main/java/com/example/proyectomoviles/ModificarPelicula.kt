package com.example.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class ModificarPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_pelicula)

        val peliculaId = intent.getIntExtra("pelicula_id", 0)
        var nombreOriginal = intent.getStringExtra("pelicula_nombre") ?: ""
        var descripcionOriginal = intent.getStringExtra("pelicula_descripcion") ?: ""
        val imagen = intent.getIntExtra("pelicula_imagen", 0)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val imgPelicula = findViewById<ImageView>(R.id.imgPelicula)
        imgPelicula.setImageResource(imagen)

        etNombre.setText(nombreOriginal)
        etDescripcion.setText(descripcionOriginal)

        btnGuardar.setOnClickListener {
            val nuevoNombre = etNombre.text.toString()
            val nuevaDescripcion = etDescripcion.text.toString()

            if (nuevoNombre != nombreOriginal || nuevaDescripcion != descripcionOriginal) {
                val dbHelper = BD_Peliculas(this)
                dbHelper.modificarPelicula(peliculaId, nuevoNombre, nuevaDescripcion)
            }

        }
        btnVolver.setOnClickListener{
            finish()
        }
    }
}