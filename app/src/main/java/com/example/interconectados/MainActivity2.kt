package com.example.interconectados

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.app.Notification.Action
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.IOException

@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity() {
    // Variables a ocupar
    private lateinit var botonBack: AppCompatImageButton
    private lateinit var botonCamara: AppCompatImageButton
    private lateinit var botonGaleria: AppCompatImageButton
    private lateinit var botonGrabar: AppCompatImageButton
    private lateinit var botonPlay: AppCompatImageButton
    private lateinit var  botonlisto: Button
    private var grabadora:MediaRecorder?=null
    var ruta:String?=null
    private val REQUEST_GALLERY = 1001
    var activo = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        // Botones de la cámara
        botonBack = findViewById(R.id.back)
        botonCamara = findViewById(R.id.camara)
        botonGaleria = findViewById(R.id.botongaleria)
        botonGrabar = findViewById(R.id.botongrabar)
        botonPlay = findViewById(R.id.butonplay)
        botonlisto = findViewById(R.id.enviar)


        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity2, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 1000)
        }

        // Acciones de los botones
        botonlisto.setOnClickListener {
            Toast.makeText(applicationContext,"Informacion enviada",Toast.LENGTH_SHORT).show()
            finish()
        }
        botonBack.setOnClickListener {
            finish()
        }
        botonCamara.setOnClickListener {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        botonGaleria.setOnClickListener{
            if (Build.VERSION.SDK_INT> Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permisoArchivo = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permisoArchivo,REQUEST_GALLERY)
                }else{
                    muestragaleria()
                }
            }else{
                muestragaleria()
            }
        }
        botonGrabar.setOnClickListener {
            if(activo == 0){
                botonGrabar.setBackgroundColor(Color.RED)
                Toast.makeText(applicationContext,"Grabando",Toast.LENGTH_SHORT).show()
                activo = 1
            }else{
                botonGrabar.setBackgroundColor(Color.GRAY)
                Toast.makeText(applicationContext,"Termino de grabar",Toast.LENGTH_SHORT).show()
                activo = 0
            }
        }
        botonPlay.setOnClickListener {
            Toast.makeText(applicationContext,"Reproduciendo audio",Toast.LENGTH_SHORT).show()
        }
    }

    //para saber si el usuario acepto o no los permisos de los archivos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_GALLERY->{
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    muestragaleria()
                }else{
                    Toast.makeText(applicationContext,"Porfavor active los permisos",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            val imageView = findViewById<ImageView>(R.id.imgcamara)
            imageView.setImageBitmap(imageBitmap)
        }
    }
    private fun muestragaleria(){
        val intentGaleria = Intent(Intent.ACTION_PICK)

        intentGaleria.type = "image/*"
        startActivityForResult(intentGaleria , REQUEST_GALLERY)
    }
    //Sacamos la img que se seleciona
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY){
            val image = findViewById<ImageView>(R.id.imgcamara)
            image.setImageURI(data?.data)
        }
    }

    fun grabar(view : View){
        botonGrabar = findViewById(R.id.botongrabar)
        if(grabadora==null) {
            ruta = getExternalStorageDirectory().absolutePath.toString() + "/grabacion.mp3"
            grabadora = MediaRecorder()
            grabadora?.setAudioSource(MediaRecorder.AudioSource.MIC)
            grabadora?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            grabadora?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            grabadora?.setOutputFile(ruta)
            try {
                grabadora?.prepare()
                grabadora?.start()
                botonGrabar.setBackgroundColor(Color.RED)
                Toast.makeText(applicationContext,"Grabando",Toast.LENGTH_SHORT).show()
            }catch (e:IOException){
                println(e)
            }
        }else{
            try {
                grabadora?.stop()
                grabadora?.release()
                botonGrabar.setBackgroundColor(Color.WHITE)
                Toast.makeText(applicationContext,"Termino de grabar",Toast.LENGTH_SHORT).show()
            }catch (e:IOException){
                println(e)
            }

        }




    }
    fun play(view:View){
        var mediaPlay =MediaPlayer()
        try {
            mediaPlay.setDataSource(ruta)
            mediaPlay.prepare()
        }catch (e:IOException){
            println(e)
        }
        mediaPlay.start()
        Toast.makeText(applicationContext,"Reproduciendor",Toast.LENGTH_SHORT).show()
    }


}



