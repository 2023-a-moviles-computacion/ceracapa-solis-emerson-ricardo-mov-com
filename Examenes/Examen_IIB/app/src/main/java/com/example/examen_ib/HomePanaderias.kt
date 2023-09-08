package com.example.examen_ib


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomePanaderias :  AppCompatActivity() {
    val db = Firebase.firestore
    val panaderias = db.collection("Panaderias")
    var idSelectItem = 0
    var adaptador: ArrayAdapter<Panaderia>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_panaderia)
        Log.i("ciclo-vida", "onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        listarPanaderias()

        val btnAnadirPanaderia = findViewById<Button>(R.id.btn_crear_new_panaderia)
        btnAnadirPanaderia.setOnClickListener {
            val intentAddPanaderia = Intent(this, CrearPanaderia::class.java)
            startActivity(intentAddPanaderia)
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idSelectItem = id
        Log.i("context-menu", "ID ${id}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var panaderiaSeleccionada:Panaderia = adaptador!!.getItem(idSelectItem)!!

        return when (item.itemId) {
            R.id.mi_editar -> {
                Log.i("context-menu", "Edit position: ${panaderiaSeleccionada.idPanaderia}")
                val abrirEditarPanaderia = Intent(this,EditarPanaderia::class.java)
                abrirEditarPanaderia.putExtra("PosPanaderia",panaderiaSeleccionada)
                startActivity(abrirEditarPanaderia)
                return true
            }
            R.id.mi_eliminar -> {
                Log.i("context-menu", "Delete position: ${idSelectItem}")
                panaderias.document("${panaderiaSeleccionada.idPanaderia}").delete()
                    .addOnSuccessListener {
                        Log.i("Eliminar-Panaderia","Success")
                    }
                    .addOnFailureListener {
                        Log.i("Eliminar-Panaderia","Failed")
                    }
                listarPanaderias()
                return true
            }
            R.id.mi_panes -> {
                Log.i("context-menu", "Panes: ${idSelectItem}")
                val abrirHomePanes = Intent(this, HomePanes::class.java)
                abrirHomePanes.putExtra("PosPanesX",panaderiaSeleccionada)
                startActivity(abrirHomePanes)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    fun listarPanaderias(){
        val lv_panaderias = findViewById<ListView>(R.id.lv_panaderias_lista)
        panaderias.get().addOnSuccessListener { result->
            var listPanaderias = arrayListOf<Panaderia>()
            for(document in result){
                listPanaderias.add(
                    Panaderia(
                        document.id.toString(),
                        document.get("nombrePanaderia").toString(),
                        document.get("ubicacionPanaderia").toString(),
                        document.get("esCafeteria").toString(),
                        document.get("arriendoPanaderia").toString().toDouble(),
                        document.get("anioFundacion").toString().toInt(),
                    )
                )
            }
            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                listPanaderias
            )
            lv_panaderias.adapter = adaptador
            adaptador!!.notifyDataSetChanged()
            registerForContextMenu(lv_panaderias)
        }.addOnFailureListener {
            Log.i("Error", "Creacion de lista de panaderias fallida")
        }
    }
}