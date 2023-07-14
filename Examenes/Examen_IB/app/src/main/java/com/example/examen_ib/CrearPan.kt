package com.example.examen_ib

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class CrearPan : AppCompatActivity {
    var nextIdPan = 0
    var lastIdPan = 0

    var nextIdPP = 0
    var lastIdPP = 0
    var idSelectedPan = 0
    var panaderiaPos = 0
    var idSelectedPanaderia = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ciclo-vida","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pan)
    }


}