package com.example.proyectomoviles

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class BD_Peliculas(context: Context) : SQLiteOpenHelper(context,"Peliculas",null,1) {
    companion object{
        val imgDefault = R.drawable.ic_pelicula_default
    }
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "CREATE TABLE Peliculas(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "nombre TEXT," +
                        "descripcion TEXT," +
                        "imagen INTEGER);"
            )
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS Peliculas")
            onCreate(db)
        }
    }

    fun inicializarBaseDeDatos() {
        val db = this.writableDatabase
        insertarPeliculaIniciales(db, "El Señor de los Anillos", "Frodo Bolsón es un hobbit...", R.drawable.ic_pelicula1)
        insertarPeliculaIniciales(db, "Volver al Futuro", "Una máquina del tiempo transporta a un adolescente...", R.drawable.ic_pelicula2)
        insertarPeliculaIniciales(db, "Spiderman", "Luego de sufrir la picadura de una araña genéticamente modificada...", R.drawable.ic_pelicula3)
        insertarPeliculaIniciales(db,"Titanic","Jack es un joven artista que gana un pasaje para viajar a América en...",R.drawable.ic_pelicula4)
        insertarPeliculaIniciales(db,"Terminator","En el año 2029 las máquinas dominan el mundo. Los rebeldes que...",R.drawable.ic_pelicula5)
        insertarPeliculaIniciales(db,"Toy Story","Los juguetes de Andy, un niño de seis años, temen que un nuevo...",R.drawable.ic_pelicula6)
        insertarPeliculaIniciales(db,"Scream","Un asesino en serie, con máscara y disfraz negro, siembra el pánico...",R.drawable.ic_pelicula7)
        insertarPeliculaIniciales(db,"Die Hard","Un grupo terrorista se apodera de un edificio de Los Ángeles...",R.drawable.ic_pelicula8)
        db.close()
    }

    private fun insertarPeliculaIniciales(db: SQLiteDatabase, nombre: String, descripcion: String, imagen: Int) {
        val cv = ContentValues()
        cv.put("nombre", nombre)
        cv.put("descripcion", descripcion)
        cv.put("imagen", imagen)
        db.insert("Peliculas", null, cv)
    }

    @SuppressLint("Range")
    fun obtenerPeliculas(): List<Pelicula> {
        val peliculas = mutableListOf<Pelicula>()
        val db = this.readableDatabase

        val query = "SELECT * FROM Peliculas"
        val cursor = db.rawQuery(query, null)

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val descripcion = cursor.getString(cursor.getColumnIndex("descripcion"))
                val imagen = cursor.getInt(cursor.getColumnIndex("imagen"))

                val pelicula = Pelicula(id, nombre, descripcion, imagen)
                peliculas.add(pelicula)
            }
        } finally {
            cursor.close()
            db.close()
        }
        return peliculas
    }
    fun isBaseDeDatosInicializada(): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery("SELECT * FROM Peliculas", null)
        val isInitialized = cursor.count > 0
        cursor.close()
        db.close()
        return isInitialized
    }
    fun eliminarPelicula(id: Int) {
        val db = writableDatabase
        db.delete("Peliculas", "id=?", arrayOf(id.toString()))
        db.close()
    }
    fun modificarPelicula(id: Int, nuevoNombre: String, nuevaDescripcion: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nombre", nuevoNombre)
            put("descripcion", nuevaDescripcion)
        }

        db.update("Peliculas", values, "id=?", arrayOf(id.toString()))
        db.close()
    }
    fun insertarPelicula(db:SQLiteDatabase, nombre: String, descripcion: String){
        val cv = ContentValues()
        cv.put("nombre",nombre)
        cv.put("descripcion",descripcion)
        cv.put("imagen", imgDefault)

        db.insert("Peliculas",null,cv)
    }
}