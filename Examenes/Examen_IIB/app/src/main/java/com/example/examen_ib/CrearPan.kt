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

class CrearPan : AppCompatActivity() {
    var panaderiaSeleccionada = Panaderia("","","","",0.0,0)
    val db = Firebase.firestore
    val panaderias = db.collection("Panaderias")
    val panes = db.collection("Panes")
    var idSelectPan = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ciclo-vida","onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pan)
    }

    override fun onStart() {
        super.onStart()

        Log.i("ciclo-vida","onStart")

        panaderiaSeleccionada = intent.getParcelableExtra<Panaderia>("posicionPanaderia")!!
        val panaderiaSubColeccion = panaderias.document("${panaderiaSeleccionada.idPanaderia}")
            .collection("Panes")


        var txtInNombre = findViewById<TextInputEditText>(R.id.txtIn_nombrePan_crear)
        var txtInOrigen = findViewById<TextInputEditText>(R.id.txtIn_origenPan_crear)
        var txtInEsDulce = findViewById<TextInputEditText>(R.id.txtIn_esDulcePan_crear)
        var txtInPrecio = findViewById<TextInputEditText>(R.id.txtIn_precioPan_crear)
        var txtInStock = findViewById<TextInputEditText>(R.id.txtIn_stockPan_crear)

        Log.i("posPan", "${panaderiaSeleccionada.idPanaderia}")

        var btnAddPan = findViewById<Button>(R.id.btn_crearPan)
        btnAddPan.setOnClickListener {
            var pan = hashMapOf(
                "nombrePan" to txtInNombre.text.toString(),
                "origenPan" to txtInOrigen.text.toString(),
                "esDulcePan" to txtInEsDulce.text.toString(),
                "precioPan" to txtInPrecio.text.toString(),
                "stockPan" to txtInStock.text.toString()
            )
            panaderiaSubColeccion.add(pan).addOnSuccessListener {
                Toast.makeText(this, "Pan registrado con exito", Toast.LENGTH_SHORT).show();
                Log.i("Crear-Pan", "Success")
            }.addOnFailureListener {
                Log.i("Crear-Pan", "Failed")
            }

            val intentAddSucces = Intent(this, HomePanes::class.java)
            startActivity(intentAddSucces)
        }


        var btnCancelarCrearPan = findViewById<Button>(R.id.btn_cancelar_crear_pan)
        btnCancelarCrearPan.setOnClickListener {
            answer()
        }


    }

    fun answer(){
        val intentReturnParameters = Intent()
        intentReturnParameters.putExtra("posicionPanaderia",panaderiaSeleccionada)
        setResult(
            RESULT_OK,
            intentReturnParameters
        )
        finish()
    }


}