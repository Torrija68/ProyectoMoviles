package com.example.proyectomoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

@Suppress("PrivatePropertyName", "LocalVariableName")
class Login : AppCompatActivity() {
    private lateinit var btningresar: Button
    private lateinit var usuario: EditText
    private lateinit var contraseña: EditText
    private lateinit var db: BD_Usuarios
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btningresar = findViewById(R.id.btnLogin)
        usuario = findViewById(R.id.etUsuarioLogin)
        contraseña = findViewById(R.id.etContraseñaLogin)
        db = BD_Usuarios(this)

        btningresar.setOnClickListener {
            val txtUsuarioLogin = usuario.text.toString()
            val txtContraseñaLogin = contraseña.text.toString()

            if (TextUtils.isEmpty(txtUsuarioLogin) || TextUtils.isEmpty(txtContraseñaLogin)) {
                Toast.makeText(this, "Ingresa un Usuario y Contraseña", Toast.LENGTH_SHORT).show()
            } else {
                if (db.tablaUsuariosExiste()) {
                    val verificarUsuario = db.LeerUsuario(txtUsuarioLogin, txtContraseñaLogin)
                    if (verificarUsuario) {
                        Toast.makeText(this, "Ingreso Exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, MenuPrincipal::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Usuario y Contraseña Incorrectos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "No hay usuarios registrados. Crea un usuario", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}