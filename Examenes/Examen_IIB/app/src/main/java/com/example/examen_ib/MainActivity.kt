package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PanaderiaBDD.TablaPanaderia = ESqliteHelperPanaderiaPan (this)
        Registers.arregloPanaderiasPanes
        val btnStart = findViewById<Button>(R.id.btn_examStart)
        btnStart.setOnClickListener{
            val intent = Intent(this, HomePanaderias::class.java)
            startActivity(intent)
        }

    }
}