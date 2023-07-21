package com.example.interconectados

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity3 : AppCompatActivity() {

    private lateinit var botonRe:Button
    private lateinit var botonIn:Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)



        botonRe = findViewById(R.id.regis)
        botonIn = findViewById(R.id.ini)


        botonRe.setOnClickListener {
            Toast.makeText(applicationContext,"Registro exitoso", Toast.LENGTH_SHORT).show()
            finish()
        }

        botonIn.setOnClickListener {
            finish()
        }
    }
}