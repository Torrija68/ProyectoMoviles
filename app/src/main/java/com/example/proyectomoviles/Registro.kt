package com.example.proyectomoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class Registro : AppCompatActivity() {

    private lateinit var etUsuario:EditText
    private lateinit var etContraseña : EditText
    private lateinit var etConfirm : EditText
    private lateinit var btnRegistro : EditText
    private lateinit var db : BasesDeDatos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
    }
}