package com.example.proyectomoviles

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BasesDeDatos(context:Context) : SQLiteOpenHelper(context,"Usuarios",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        TODO("create table Usuarios (usuario TEXT primary key, contraseña TEXT")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("drop table if exists Usuarios")
    }

    fun CrearUsuario(usuario: String, contraseña: String): Boolean {
        val p0 = this.writableDatabase
        val cv = ContentValues()
        cv.put("usuario", usuario)
        cv.put("contraseña",contraseña)
        val result = p0.insert("usuario",null,cv)
        if (result == -1.toLong()){
            return false
        }
        return true
    }

    fun verificarUsuario(usuario: String, contraseña: String): Boolean {
        val p0 = this.writableDatabase
        val query = "select * from Usuarios where usuario = '$usuario' and contraseña = '$contraseña'"
        val cursor = p0.rawQuery(query,null)
        if(cursor.count<=0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }
}