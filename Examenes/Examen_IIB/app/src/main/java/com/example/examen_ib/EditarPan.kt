package com.example.examen_ib

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarPan : AppCompatActivity() {
    var panaderiaSeleccionada = Panaderia("","","","",0.0,0)
    var panSeleccionado = Pan("","","","","",0.0,0)
    val db = Firebase.firestore
    val panaderias = db.collection("Panaderias")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_pan)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        panaderiaSeleccionada = intent.getParcelableExtra<Panaderia>("posicionPanaderiaEditar")!!
        panSeleccionado = intent.getParcelableExtra<Pan>("pan")!!

        // ------------------ o ------------------

        val editNombrePan = findViewById<TextInputEditText>(R.id.txtIn_nombrePan_editar)
        val editOrigenPan = findViewById<TextInputEditText>(R.id.txtIn_origenPan_editar)
        val editDulcePan = findViewById<TextInputEditText>(R.id.txtIn_esDulcePan_editar)
        val editPrecioPan = findViewById<TextInputEditText>(R.id.txtIn_precioPan_editar)
        val editStockPan = findViewById<TextInputEditText>(R.id.txtIn_stockPan_editar)

        editNombrePan.setText(panSeleccionado.nombrePan.toString())
        editOrigenPan.setText(panSeleccionado.origenPan.toString())
        editDulcePan.setText(panSeleccionado.esDulce.toString())
        editPrecioPan.setText(panSeleccionado.precioPan.toString())
        editStockPan.setText(panSeleccionado.stockPan.toString())
        // ------------------ o ------------------

        val btnEditPan = findViewById<Button>(R.id.btn_editarPan)
        btnEditPan.setOnClickListener {
            panaderias.document("${panaderiaSeleccionada.idPanaderia}")
                .collection("Panes")
                .document("${panSeleccionado.idPan}")
                .update(
                    "nombrePan", editNombrePan.text.toString(),
                    "origenPan", editOrigenPan.text.toString(),
                    "esDulce", editDulcePan.text.toString(),
                    "precioPan", editPrecioPan.text.toString().toDouble(),
                    "stockPan", editStockPan.text.toString().toInt()
                )
                .addOnSuccessListener {
                    Toast.makeText(this, "Pan actualizado con éxito", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "${panaderiaSeleccionada.idPanaderia}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "${panSeleccionado.idPan}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Error al actualizar el pan: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            answer()
        }


        val btnCancelPan = findViewById<Button>(R.id.btn_cancelar_editar_pan)
        btnCancelPan.setOnClickListener {
            answer()
        }


    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicionPanaderiaEditar",panaderiaSeleccionada)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }
}
