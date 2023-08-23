package com.example.voicemod

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Configuracion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)

        val textView = findViewById<TextView>(R.id.textView3)
        textView.text = "support@voicemod.net"
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        val botonMenu = findViewById<Button>(R.id.menu)
        botonMenu.setOnClickListener {irActividad(MainActivity::class.java) }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}