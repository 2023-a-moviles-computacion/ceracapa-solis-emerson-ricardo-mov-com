package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class CrearPan : AppCompatActivity() {
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

    override fun onStart() {
        super.onStart()

        Log.i("ciclo-vida","onStart")
        panaderiaPos = intent.getIntExtra("posicionPanaderia",-1)
        Log.i("posPanaderia","${panaderiaPos}")

        PanaderiaBDD.TablaPanaderia!!.listarPanaderias().forEachIndexed { indice: Int, panaderia: Panaderia ->
            if(indice==panaderiaPos){
                idSelectedPanaderia = panaderia.idPanaderia
            }
        }

        var longListPan = PanaderiaBDD.TablaPanaderia!!.listarPanes().lastIndex

        PanaderiaBDD.TablaPanaderia!!.listarPanes().forEachIndexed { indice: Int, pan: Pan ->
            Log.i("testExamen","${pan.idPan} -> ${pan.nombrePan}")
            if(indice == longListPan){
                lastIdPan = pan.idPan
            }
        }
        nextIdPan = lastIdPan+1

        var longPP = Registers.arregloPanaderiasPanes.lastIndex
        Registers.arregloPanaderiasPanes.forEachIndexed { indice: Int, panaderiasPanes: PanaderiasPanes ->
            if(indice==longPP)
                lastIdPP = panaderiasPanes.idPanaderiaPan
        }
        nextIdPP = lastIdPP+1


        // ------------ o ------------

        var txtInNombre = findViewById<TextInputEditText>(R.id.txtIn_nombrePan_crear)
        var txtInOrigen = findViewById<TextInputEditText>(R.id.txtIn_origenPan_crear)
        var txtInEsDulce = findViewById<TextInputEditText>(R.id.txtIn_esDulcePan_crear)
        var txtInPrecio = findViewById<TextInputEditText>(R.id.txtIn_precioPan_crear)
        var txtInStock = findViewById<TextInputEditText>(R.id.txtIn_stockPan_crear)

        var btnAddPan = findViewById<Button>(R.id.btn_crearPan)
        btnAddPan.setOnClickListener {
            var nombrePan = txtInNombre.text.toString()
            var origenPan = txtInOrigen.text.toString()
            var esDulcePan = txtInEsDulce.text.toString()
            val precioPan = txtInPrecio.text.toString()
            var stockPan = txtInStock.text.toString()

            Registers.arregloPanaderiasPanes.add(PanaderiasPanes(nextIdPP, idSelectedPanaderia, nextIdPan))

            PanaderiaBDD.TablaPanaderia!!.crearPan(nextIdPan, nombrePan,origenPan,esDulcePan,precioPan,stockPan)

            answer()
        }

        // ------------ o ------------

        var btnCancelarCrearPan = findViewById<Button>(R.id.btn_cancelar_crear_pan)
        btnCancelarCrearPan.setOnClickListener {
            answer()
        }


    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicionPanaderia",panaderiaPos)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }


}