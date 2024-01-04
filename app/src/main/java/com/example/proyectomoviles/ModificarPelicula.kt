package com.example.proyectomoviles

import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.NotificationCompat

class ModificarPelicula : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_pelicula)

        val peliculaId = intent.getIntExtra("pelicula_id", 0)
        var nombreOriginal = intent.getStringExtra("pelicula_nombre") ?: ""
        var descripcionOriginal = intent.getStringExtra("pelicula_descripcion") ?: ""
        val imagen = intent.getIntExtra("pelicula_imagen", 0)
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_guardar)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val imgPelicula = findViewById<ImageView>(R.id.imgPelicula)
        imgPelicula.setImageResource(imagen)

        etTitulo.setText(nombreOriginal)
        etDescripcion.setText(descripcionOriginal)

        btnGuardar.setOnClickListener {

            val nuevoTitulo = etTitulo.text.toString()
            val nuevaDescripcion = etDescripcion.text.toString()

            if(!(nuevoTitulo.isNotEmpty() && nuevaDescripcion.isNotEmpty())) {
                Toast.makeText(this,"No se pueden dejar espacios en blanco",Toast.LENGTH_SHORT).show()
            } else if (nuevoTitulo != nombreOriginal || nuevaDescripcion != descripcionOriginal) {
                mediaPlayer?.start()
                val dbHelper = BD_Peliculas(this)
                dbHelper.modificarPelicula(peliculaId, nuevoTitulo, nuevaDescripcion)
                NotificacionModificarPelicula(nuevoTitulo)
            }

        }
        btnVolver.setOnClickListener{
            finish()
        }
    }
    private fun NotificacionModificarPelicula(titulo : String){
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
            .setContentTitle("Película Modificada")
            .setContentText("Has Modificado la película: $titulo")
            .setSmallIcon(R.drawable.ic_notificacion_modificar)
            .build()

        notificationManager.notify(1, notification)
    }
    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

}