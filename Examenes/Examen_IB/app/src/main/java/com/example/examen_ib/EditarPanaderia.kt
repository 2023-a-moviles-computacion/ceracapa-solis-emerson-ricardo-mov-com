package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditarPanaderia : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ciclo-vida", "onCreate")
        setContentView(R.layout.activity_editar_panaderia)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        val posicionPanaderia = intent.getIntExtra("posicionEditar",1)

        var editNombre = findViewById<TextInputEditText>(R.id.txtIn_nombrePanaderia_editar)
        var editUbicacion = findViewById<TextInputEditText>(R.id.txtIn_ubicacionPanaderia_editar)
        var editEsCafeteria = findViewById<TextInputEditText>(R.id.txtIn_esCafeteriaPanaderia_editar)
        var editArriendo = findViewById<TextInputEditText>(R.id.txtIn_arriendoPanaderia_editar)
        var editAnioF = findViewById<TextInputEditText>(R.id.txtIn_anioFundacionPanaderia_editar)

        PanaderiaBDD.TablaPanaderia!!.listarPanaderias().forEachIndexed{ indice: Int, panaderia: Panaderia ->
            if(indice == posicionPanaderia){
                editNombre.setText(panaderia.nombrePanaderia.toString())
                editUbicacion.setText(panaderia.ubicacionPanaderia.toString())
                editEsCafeteria.setText(panaderia.esCafeteria.toString())
                editArriendo.setText(panaderia.arriendoPanaderia.toString())
                editAnioF.setText(panaderia.anioFundacion.toString())
            }
        }

        val btnGuardarCambios = findViewById<Button>(R.id.btn_editarPanaderia)
        btnGuardarCambios.setOnClickListener {
            var nombrePanaderia = editNombre.text.toString()
            var ubicacionPanaderia = editUbicacion.text.toString()
            var esCafePanaderia = editEsCafeteria.text.toString()
            var arriendoPanaderia = editArriendo.text.toString().toDouble()
            var anioFPanaderia = editAnioF.text.toString().toInt()

            PanaderiaBDD.TablaPanaderia!!.actualizarPanaderia(posicionPanaderia, nombrePanaderia,
                ubicacionPanaderia, esCafePanaderia, arriendoPanaderia.toString(), anioFPanaderia.toString())

            val intentEditSucces = Intent(this, HomePanaderias::class.java)
            startActivity(intentEditSucces)
        }

        val btnCancelarEditar = findViewById<Button>(R.id.btn_cancelar_editar)
        btnCancelarEditar.setOnClickListener{
            val intentEditCancel = Intent(this, HomePanaderias::class.java)
            startActivity(intentEditCancel)
        }

    }
}