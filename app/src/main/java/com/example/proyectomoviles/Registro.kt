package com.example.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Registro : AppCompatActivity() {

    private lateinit var usuario:EditText
    private lateinit var contraseña : EditText
    private lateinit var confirm : EditText
    private lateinit var btnregistro : Button
    private lateinit var db : BD_Usuarios
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        usuario = findViewById(R.id.etUsuario)
        contraseña = findViewById(R.id.etContraseña)
        confirm = findViewById(R.id.etConfirm)
        btnregistro = findViewById(R.id.btnRegistro)
        db = BD_Usuarios(this)

        btnregistro.setOnClickListener{
            val txtUsuario = usuario.text.toString().trim()
            val txtContraseña = contraseña.text.toString().trim()
            val txtConfirmar = confirm.text.toString().trim()
            val datosGuardados = db.CrearUsuario(txtUsuario,txtContraseña)

            if(TextUtils.isEmpty(txtUsuario) || TextUtils.isEmpty(txtContraseña) || TextUtils.isEmpty(txtConfirmar)){
                Toast.makeText(this,"Crea un Usuario y una Contraseña",Toast.LENGTH_SHORT).show()
            }
            else{
                if(txtContraseña.equals(txtConfirmar)){
                    if(datosGuardados == true){
                        Toast.makeText(this,"Registro Exitoso",Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext,Login::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this,"El usuario ya existe",Toast.LENGTH_SHORT).show()
                    }
                } else{
                    Toast.makeText(this,"Las contraseñas no son iguales",Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}