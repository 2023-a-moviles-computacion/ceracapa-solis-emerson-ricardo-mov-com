package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class CrearPanaderia : AppCompatActivity() {

    var id1 = 0
    var id2 = 0

    var nombrePanaderia = ""
    var ubicacionPanaderia = ""
    var esCafeteriaPanaderia = ""
    var arriendoPanaderia = ""
    var anioFundacion = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_panaderia)
    }

    override fun onStart() {
        super.onStart()
        Log.i("cicloVida", "onStart")
        var longListPanaderia = PanaderiaBDD.TablaPanaderia!!.listarPanaderias().lastIndex
        PanaderiaBDD.TablaPanaderia!!.listarPanaderias().forEachIndexed { indice: Int, panaderia: Panaderia ->
            Log.i("testExamen","${panaderia.idPanaderia} -> ${panaderia.nombrePanaderia}")
            if( indice == longListPanaderia){
                id2 = panaderia.idPanaderia
            }
        }

        id1 = id2+1

        var txtInNombre = findViewById<TextInputEditText>(R.id.txtIn_nombrePanaderia_crear)
        var txtInUbicacion = findViewById<TextInputEditText>(R.id.txtIn_ubicacionPanaderia_crear)
        var txtInEsCafeteria = findViewById<TextInputEditText>(R.id.txtIn_esCafeteriaPanaderia_crear)
        var txtInArriendo = findViewById<TextInputEditText>(R.id.txtIn_arriendoPanaderia_crear)
        var txtInAnioF = findViewById<TextInputEditText>(R.id.txtIn_anioFundacionPanaderia_crear)

        var btnCrearPanaderia = findViewById<Button>(R.id.btn_crearPanaderia)

        btnCrearPanaderia.setOnClickListener {
            nombrePanaderia = txtInNombre.text.toString()
            ubicacionPanaderia = txtInUbicacion.text.toString()
            esCafeteriaPanaderia = txtInEsCafeteria.text.toString()
            arriendoPanaderia = txtInArriendo.text.toString()
            anioFundacion = txtInAnioF.text.toString()

            PanaderiaBDD.TablaPanaderia!!.crearPanaderia(id1,nombrePanaderia,ubicacionPanaderia,
                esCafeteriaPanaderia, arriendoPanaderia, anioFundacion)

            val intentAddSucces = Intent(this, HomePanaderias::class.java)
            startActivity(intentAddSucces)

        }

        var btnCancelarCrearPanaderia = findViewById<Button>(R.id.btn_cancelar_crear)
        btnCancelarCrearPanaderia.setOnClickListener {
            val intentAddCancel = Intent(this, HomePanaderias::class.java)
            startActivity(intentAddCancel)
        }

    }
}