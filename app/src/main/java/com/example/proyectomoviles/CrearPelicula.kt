package com.example.proyectomoviles

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat

class CrearPelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pelicula)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val imgPelicula = findViewById<ImageView>(R.id.imgPelicula)
        imgPelicula.setImageResource(R.drawable.ic_pelicula_default)

        btnGuardar.setOnClickListener {
            val titulo = etTitulo.text.toString()
            val descripcion = etDescripcion.text.toString()


            if(titulo.isNotEmpty() && descripcion.isNotEmpty()){
                val dbHelper = BD_Peliculas(this)
                val db= dbHelper.writableDatabase
                dbHelper.insertarPelicula(db,titulo, descripcion)

                NotificacionAgregarPelicula(titulo)

                db.close()
                finish()
            } else{
                Toast.makeText(this,"No se puede guardar, espacios en blanco", Toast.LENGTH_SHORT).show()
            }
        }
        btnVolver.setOnClickListener {
            finish()
        }
    }
    private fun NotificacionAgregarPelicula(titulo : String){
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "mi_canal",
                "Nombre del Canal",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "mi_canal")
            .setContentTitle("Película Creada")
            .setContentText("Has creado la película: $titulo")
            .setSmallIcon(R.drawable.ic_notificacion_agregar)
            .build()

        notificationManager.notify(1, notification)
    }
}