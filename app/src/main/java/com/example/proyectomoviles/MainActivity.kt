package com.example.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnIngresar: Button
    private lateinit var btnRegistro: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIngresar = findViewById(R.id.btnIngresar)
        btnRegistro = findViewById(R.id.btnRegistrarse)

        btnRegistro.setOnClickListener{
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }
        btnIngresar.setOnClickListener {
            val intent = Intent(this,Registro::class.java)
            startActivity(intent)
        }
    }


}