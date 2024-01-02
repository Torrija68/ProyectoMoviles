package com.example.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class CrearPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val imgPelicula = findViewById<ImageView>(R.id.imgPelicula)
        imgPelicula.setImageResource(R.drawable.ic_pelicula_default)

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val descripcion = etDescripcion.text.toString()

            if(titulo.isNotEmpty() && descripcion.isNotEmpty()){
                val dbHelper = BD_Peliculas(this)
                val db= dbHelper.writableDatabase
                dbHelper.insertarPelicula(db,titulo, descripcion)
                db.close()
                finish()
            } else{
                Toast.makeText(this,"No se puede guardar, espacios en blanco", Toast.LENGTH_SHORT).show()
            }
        }
    }
}