package com.example.proyectomoviles

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MenuPrincipal : AppCompatActivity() {
    private lateinit var dbHelper: BD_Peliculas
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PeliculasAdapter
    private var peliculasList: MutableList<Pelicula> = mutableListOf()
    private var mediaPlayer: MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        dbHelper = BD_Peliculas(this)
        // dbHelper.inicializarBaseDeDatos()
        recyclerView = findViewById(R.id.recyclerViewPeliculas)
        mediaPlayer = MediaPlayer.create(this, R.raw.sonido_eliminar)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        adapter = PeliculasAdapter(peliculasList,
            onDeleteClickListener = { position ->
                val nombrePelicula = peliculasList[position].nombre
                dbHelper.eliminarPelicula(peliculasList[position].id)
                peliculasList.removeAt(position)
                adapter.notifyDataSetChanged()

                NotificacionEliminarPelicula(nombrePelicula)
                mediaPlayer?.start()
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
    private fun NotificacionEliminarPelicula(nombrePelicula : String){
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
            .setContentTitle("Película Eliminada")
            .setContentText("Has eliminado la película: $nombrePelicula")
            .setSmallIcon(R.drawable.ic_notificacion_eliminar)
            .build()
        notificationManager.notify(1, notification)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}