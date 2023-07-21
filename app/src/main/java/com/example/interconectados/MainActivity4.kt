package com.example.interconectados

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity4 : AppCompatActivity() {

    private lateinit var botonRegistro:Button
    private lateinit var botonInicio:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        fun cambiarpantallaregistro(){
            val intent : Intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }

        botonRegistro = findViewById(R.id.registro)
        botonInicio = findViewById(R.id.inicio)
        botonRegistro.setOnClickListener {
            cambiarpantallaregistro()
        }
        botonInicio.setOnClickListener {
            Toast.makeText(applicationContext,"Sesion iniciada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}