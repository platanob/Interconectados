package com.example.interconectados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton


class MainActivity : AppCompatActivity() {

    private lateinit var botonEmergencia: Button
    private lateinit var botonLoggin: AppCompatImageButton
    fun  alertaBoton(texto: String){

        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(this, texto , duration)
        toast.show()
    }

    fun cambiarpantalla(){
        val intent : Intent=Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
    fun cambiarpantallalogin(){
        val intent : Intent=Intent(this, MainActivity4::class.java)
        startActivity(intent)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // botones

        botonLoggin = findViewById(R.id.imageButton)
        botonEmergencia = findViewById(R.id.botonemergencia)

        botonEmergencia.setOnClickListener {
            alertaBoton("Llamada echa")
            cambiarpantalla()
        }

        botonLoggin.setOnClickListener {
            cambiarpantallalogin()
        }


    }


}