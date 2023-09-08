package com.example.examen_ib

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomePanes : AppCompatActivity() {
    var panaderiaSeleccionada = Panaderia("","","","",0.0,0)
    val db = Firebase.firestore
    val panaderias = db.collection("Panaderias")
    var idSelectItem = 0
    var adaptador: ArrayAdapter<Pan>?=null
    var panSeleccionado: Pan? = Pan("","","","","",0.0,0)


    var resultAddPan = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                panaderiaSeleccionada = intent.getParcelableExtra<Panaderia>("posicionPanaderia")!!
            }
        }
    }

    var resultEditPan = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                panaderiaSeleccionada = intent.getParcelableExtra<Panaderia>("posicionPanaderia")!!
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_panes)
    }


    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        panaderiaSeleccionada = intent.getParcelableExtra<Panaderia>("posicionPanaderia")!!
        listViewPanes()

        val txtNombrePanaderia = findViewById<TextView>(R.id.tv_nombrePanaderia)
        txtNombrePanaderia.setText("Panaderia: "+panaderiaSeleccionada.nombrePanaderia)


        val btnNewPan = findViewById<Button>(R.id.btn_crear_new_pan)
        btnNewPan.setOnClickListener {
            abrirActividadAddPan(CrearPan::class.java)
        }

        val btnBack = findViewById<Button>(R.id.btn_volver_pan)
        btnBack.setOnClickListener {
            val intentBackPan = Intent(this, HomePanaderias::class.java)
            startActivity(intentBackPan)
        }

    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_panes, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idSelectItem = info.position
        Log.i("context-menu", "ID Pan ${idSelectItem}")
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        panSeleccionado = adaptador!!.getItem(idSelectItem)
        return when (item.itemId) {
            R.id.mi_editarPan -> {
                Log.i("context-menu", "Edit position: ${idSelectItem}")
                abrirActividadEditPan(EditarPan::class.java)
                return true
            }
            R.id.mi_eliminarPan -> {
                Log.i("context-menu", "Delete position: ${idSelectItem}")
                val panaderiaSubCollection = panaderias.document("${panaderiaSeleccionada.idPanaderia}")
                    .collection("Panes")
                    .document("${panSeleccionado!!.idPan}")
                    .delete()
                    .addOnSuccessListener {
                        Log.i("Eliminar-Pan","Success")
                    }
                    .addOnFailureListener{
                        Log.i("Eliminar-Pan","Failed")
                    }
                listViewPanes()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun listViewPanes(){
        val panaderiaSubCollection = panaderias.document("${panaderiaSeleccionada.idPanaderia}")
            .collection("Panes")
            .whereEqualTo("idPanaderia","${panaderiaSeleccionada.idPanaderia}")

        val lv_panes = findViewById<ListView>(R.id.lv_panes_lista)
        panaderiaSubCollection.get().addOnSuccessListener { result->
            var listaPanes = arrayListOf<Pan>()
            for(document in result){
                listaPanes.add(
                    Pan(
                        document.id.toString(),
                        document.data.get("idPanaderia").toString(),
                        document.data.get("nombrePan").toString(),
                        document.data.get("origenPan").toString(),
                        document.data.get("esDulce").toString(),
                        document.data.get("precioPan").toString().toDouble(),
                        document.data.get("stockPan").toString().toInt()
                    )
                )
            }
            adaptador = ArrayAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                listaPanes
            )
            lv_panes.adapter = adaptador
            adaptador!!.notifyDataSetChanged()

            registerForContextMenu(lv_panes)
        }


    }

    fun abrirActividadAddPan(
        clase: Class<*>
    ) {
        val intentAddNewPan = Intent(this, clase)
        intentAddNewPan.putExtra("posicionPanaderia",panaderiaSeleccionada)
        resultAddPan.launch(intentAddNewPan)
    }

    fun abrirActividadEditPan(
        clase: Class<*>
    ) {
        val intentEditPan = Intent(this, clase)
        intentEditPan.putExtra("pan", panSeleccionado)
        intentEditPan.putExtra("posicionPanaderiaEditar", panaderiaSeleccionada)
        resultEditPan.launch(intentEditPan)
    }
}