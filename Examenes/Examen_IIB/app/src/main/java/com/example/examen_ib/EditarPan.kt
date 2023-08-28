package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EditarPan : AppCompatActivity() {
    var panaderiaPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pan)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        panaderiaPos = intent.getIntExtra("posicionPanaderiaEditar",1)

        // ------------------ o ------------------

        val editNombrePan = findViewById<TextInputEditText>(R.id.txtIn_nombrePan_editar)
        val editOrigenPan = findViewById<TextInputEditText>(R.id.txtIn_origenPan_editar)
        val editDulcePan = findViewById<TextInputEditText>(R.id.txtIn_esDulcePan_editar)
        val editPrecioPan = findViewById<TextInputEditText>(R.id.txtIn_precioPan_editar)
        val editStockPan = findViewById<TextInputEditText>(R.id.txtIn_stockPan_editar)

        // ------------------ o ------------------

        var idPan = intent.getIntExtra("Pan",1)

        // ------------------ o ------------------

        PanaderiaBDD.TablaPanaderia!!.listarPanes().forEachIndexed { indice: Int, pan: Pan ->
            if(pan.idPan == idPan){
                // Llenar los campos
                editNombrePan.setText(pan.nombrePan)
                editOrigenPan.setText(pan.origenPan)
                editDulcePan.setText(pan.esDulce)
                editPrecioPan.setText(pan.precioPan.toString())
                editStockPan.setText(pan.stockPan.toString())
            }
        }

        // ------------------ o ------------------

        val btnEditPan = findViewById<Button>(R.id.btn_editarPan)
        btnEditPan.setOnClickListener {
            var nombrePan = editNombrePan.text.toString()
            var origenPan = editOrigenPan.text.toString()
            var esDulcePan = editDulcePan.text.toString()
            var precioPan = editPrecioPan.text.toString().toDouble()
            var stockPan = editStockPan.text.toString().toInt()

            PanaderiaBDD.TablaPanaderia!!.actualizarPan(idPan,nombrePan,origenPan,esDulcePan,
                precioPan.toString(), stockPan.toString())

            answer()
        }


        // ------------------ o ------------------

        val btnCancelPan = findViewById<Button>(R.id.btn_cancelar_editar_pan)
        btnCancelPan.setOnClickListener {
            answer()
        }


    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicionPanaderiaEditar",panaderiaPos)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }
}
