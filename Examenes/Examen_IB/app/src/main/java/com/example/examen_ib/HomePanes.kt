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

class HomePanes : AppCompatActivity() {

    var idSelectItem = 0
    var panaderiaPos = 0
    var itemSelect = 0
    var idPanaderia = 0

    var resultAddPan = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                panaderiaPos = data?.getIntExtra("posicionPanaderia",0)!!
            }
        }
    }

    var resultEditPan = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                panaderiaPos = data?.getIntExtra("posicionPanaderia",0)!!
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_panes)
    }


    fun listViewPanes():ArrayList<Pan>{
        var listaIDPanes = arrayListOf<Int>()

        Registers.arregloPanaderiasPanes.forEachIndexed { indice: Int, pp: PanaderiasPanes ->
            if(pp.idPanaderia == idPanaderia){
                listaIDPanes.add(pp.idPan)
            }
        }
        var panList = arrayListOf<Pan>()
        PanaderiaBDD.TablaPanaderia!!.listarPanes()
            .forEachIndexed { index : Int, pan: Pan ->
                for(i in listaIDPanes){
                    if(i==pan.idPan){
                        panList.add(pan)
                    }
                }
            }

        return panList
    }


    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        panaderiaPos = intent.getIntExtra("posicionEditar",1)
        PanaderiaBDD.TablaPanaderia!!.listarPanaderias().forEachIndexed { indice: Int, panaderia: Panaderia ->
            if(indice==panaderiaPos){
                val txtPanaderiaName = findViewById<TextView>(R.id.tv_nombrePanaderia)
                txtPanaderiaName.setText("Panadería: "+panaderia.nombrePanaderia)
                idPanaderia = panaderia.idPanaderia
            }
        }

        val listViewPan = findViewById<ListView>(R.id.lv_panes_lista)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listViewPanes()
        )

        listViewPan.adapter = adaptador
        adaptador.notifyDataSetChanged()

        this.registerForContextMenu(listViewPan)

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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val panListView = findViewById<ListView>(R.id.lv_panes_lista)

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listViewPanes()
        )
        panListView.adapter = adaptador
        adaptador.notifyDataSetChanged()
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
        val id = info.position
        itemSelect = id
        val idR=listViewPanes()[id].idPan
        idSelectItem = idR
        Log.i("context-menu", "ID Pan ${id}")
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editarPan -> {
                Log.i("context-menu", "Edit position: ${idSelectItem}")
                abrirActividadEditPan(EditarPan::class.java)
                return true
            }
            R.id.mi_eliminarPan -> {
                Log.i("context-menu", "Delete position: ${idSelectItem}")
                PanaderiaBDD.TablaPanaderia!!.eliminarPan(idSelectItem)

                val listVPan = findViewById<ListView>(R.id.lv_panes_lista)

                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    listViewPanes()
                )
                listVPan.adapter = adaptador
                adaptador.notifyDataSetChanged()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActividadAddPan(
        clase: Class<*>
    ) {
        val intentAddNewPan = Intent(this, clase)
        intentAddNewPan.putExtra("posicionPanaderia",panaderiaPos)
        Log.i("positionSend","${panaderiaPos}")
        resultAddPan.launch(intentAddNewPan)
    }

    fun abrirActividadEditPan(
        clase: Class<*>
    ) {
        val intentEditPan = Intent(this, clase)
        intentEditPan.putExtra("Pan", idSelectItem)
        intentEditPan.putExtra("posicionPanaderiaEditar", panaderiaPos)
        resultEditPan.launch(intentEditPan)
    }
}