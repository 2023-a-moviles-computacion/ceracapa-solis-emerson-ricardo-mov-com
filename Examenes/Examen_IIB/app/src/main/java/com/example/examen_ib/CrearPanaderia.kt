package com.example.examen_ib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearPanaderia : AppCompatActivity() {

    val db = Firebase.firestore
    val panaderias = db.collection("Panaderias")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_panaderia)
    }

    override fun onStart() {
        super.onStart()

        var txtInNombre = findViewById<TextInputEditText>(R.id.txtIn_nombrePanaderia_crear)
        var txtInUbicacion = findViewById<TextInputEditText>(R.id.txtIn_ubicacionPanaderia_crear)
        var txtInEsCafeteria = findViewById<TextInputEditText>(R.id.txtIn_esCafeteriaPanaderia_crear)
        var txtInArriendo = findViewById<TextInputEditText>(R.id.txtIn_arriendoPanaderia_crear)
        var txtInAnioF = findViewById<TextInputEditText>(R.id.txtIn_anioFundacionPanaderia_crear)

        var btnCrearPanaderia = findViewById<Button>(R.id.btn_crearPanaderia)

        btnCrearPanaderia.setOnClickListener {
            var panaderia = hashMapOf(
                "nombrePanaderia" to txtInNombre.text.toString(),
                "ubicacionPanaderia" to txtInUbicacion.text.toString(),
                "esCafeteria" to txtInEsCafeteria.text.toString(),
                "arriendoPanaderia" to txtInArriendo.text.toString(),
                "anioFundacion" to txtInAnioF.text.toString()
            )

            panaderias.add(panaderia).addOnSuccessListener {
                txtInNombre.text!!.clear()
                txtInUbicacion.text!!.clear()
                txtInEsCafeteria.text!!.clear()
                txtInArriendo.text!!.clear()
                txtInAnioF.text!!.clear()

                Toast.makeText(this,"Panaderia registrada con exito", Toast.LENGTH_SHORT).show();
                Log.i("Crear-Panaderia","Success")
            }.addOnSuccessListener {
                Log.i("Crear-Panaderia","Failed")
            }


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