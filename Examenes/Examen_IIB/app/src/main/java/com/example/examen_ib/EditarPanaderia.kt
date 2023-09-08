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

class EditarPanaderia : AppCompatActivity() {
    var panaderiaSeleccionada = Panaderia("","","","",0.0,0)
    val db = Firebase.firestore
    val panaderias = db.collection("Panaderias")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ciclo-vida", "onCreate")
        setContentView(R.layout.activity_editar_panaderia)
    }

    override fun onStart() {
        Log.i("ciclo-vida", "onStart")
        super.onStart()

        panaderiaSeleccionada = intent.getParcelableExtra<Panaderia>("PosPanaderia")!!

        val editNombre = findViewById<TextInputEditText>(R.id.txtIn_nombrePanaderia_editar)
        val editUbicacion = findViewById<TextInputEditText>(R.id.txtIn_ubicacionPanaderia_editar)
        val editEsCafeteria = findViewById<TextInputEditText>(R.id.txtIn_esCafeteriaPanaderia_editar)
        val editArriendo = findViewById<TextInputEditText>(R.id.txtIn_arriendoPanaderia_editar)
        val editAnioF = findViewById<TextInputEditText>(R.id.txtIn_anioFundacionPanaderia_editar)

        editNombre.setText(panaderiaSeleccionada.nombrePanaderia.toString())
        editUbicacion.setText(panaderiaSeleccionada.ubicacionPanaderia.toString())
        editEsCafeteria.setText(panaderiaSeleccionada.esCafeteria.toString())
        editArriendo.setText(panaderiaSeleccionada.arriendoPanaderia.toString())
        editAnioF.setText(panaderiaSeleccionada.anioFundacion.toString())

        val btnGuardarCambios = findViewById<Button>(R.id.btn_editarPanaderia)
        btnGuardarCambios.setOnClickListener {
            panaderias.document("${panaderiaSeleccionada.idPanaderia}").update(
                "nombrePanaderia", editNombre.text.toString(),
                "ubicacionPanaderia", editUbicacion.text.toString(),
                "esCafeteriaPanaderia", editEsCafeteria.text.toString(),
                "arriendoPanaderia", editArriendo.text.toString().toDouble(),
                "anioFundacion", editAnioF.text.toString().toInt()
            )

            Toast.makeText(this,"Panaderia actualizada con exito", Toast.LENGTH_SHORT).show()

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