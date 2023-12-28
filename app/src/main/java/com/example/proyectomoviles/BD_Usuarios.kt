package com.example.proyectomoviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BD_Usuarios(context:Context) : SQLiteOpenHelper(context,"Usuarios",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL("CREATE TABLE Usuarios(usuario TEXT PRIMARY KEY, contraseña TEXT);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Usuarios")
        onCreate(db)
    }

    fun CrearUsuario(usuario: String, contraseña: String): Boolean {
        if (!tablaUsuariosExiste()) {
            onCreate(this.writableDatabase)
        }
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put("usuario", usuario)
        cv.put("contraseña",contraseña)

        val result = db.insert("Usuarios",null,cv)
        return result != -1L

    }

    fun LeerUsuario(usuario: String, contraseña: String): Boolean {
        val db = this.readableDatabase
        if (!tablaUsuariosExiste()) {
            return false
        }
        val query = "SELECT * FROM Usuarios WHERE usuario = ? AND contraseña = ?"
        val cursor = db.rawQuery(query, arrayOf(usuario, contraseña))
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }
    fun tablaUsuariosExiste(): Boolean {
        val db = this.readableDatabase
        var tableExists = false
        val cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='Usuarios'", null)
        if (cursor != null) {
            tableExists = cursor.count > 0
            cursor.close()
        }

        return tableExists
    }

}