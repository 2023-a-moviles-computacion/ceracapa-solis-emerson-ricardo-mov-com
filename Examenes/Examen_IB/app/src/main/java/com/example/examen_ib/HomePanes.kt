package com.example.examen_ib

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
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
    var PanaderiaPos = 0
    var itemSelect = 0
    var idPanaderia = 0

    var resultAddPan = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                PanaderiaPos = data?.getIntExtra("posicionPanaderia",0)!!
            }
        }
    }

    var resultEditPan = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                val data = result.data
                PanaderiaPos = data?.getIntExtra("posicionPanaderia",0)!!
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_panes)
    }


    fun listViewPanes():ArrayList<Pan>{
        var listaID = arrayListOf<Int>()
        Registers.arregloPanaderiasPanes.forEachIndexed { indice: Int, pp: PanaderiasPanes ->
            if(pp.idPanaderia == idPanaderia){
                listaID.add(pp.idPan)
            }
        }
        var panList = arrayListOf<Pan>()
        PanaderiaBDD.TablaPanaderia!!.listarPanes()
            .forEachIndexed { index : Int, pan: Pan ->
                for(i in listaID){
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

        PanaderiaPos = intent.getIntExtra("posicionEditar",1)
        PanaderiaBDD.TablaPanaderia!!.listarPanaderias().forEachIndexed { indice: Int, panaderia: Panaderia ->
            if(indice==PanaderiaPos){
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

        val btnNewPan = findViewById<Button>(R.id.btn_crear_new_pan)
        btnNewPan.setOnClickListener {
            abrirActividadAddPan(CrearPan::class.java)
        }

        val btnBack = findViewById<Button>(R.id.btn_volver_pan)
        btnBack.setOnClickListener {
            val intentBackPan = Intent(this, HomePanaderias::class.java)
        }

        this.registerForContextMenu(listViewPan)
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
        Log.i("context-menu", "ID Jugador ${id}")
    }

    fun abrirActividadAddPan(
        clase: Class<*>
    ) {
        val intentAddNewPan = Intent(this, clase)
        intentAddNewPan.putExtra("posicionEquipo",PanaderiaPos)
        Log.i("positionSend","${PanaderiaPos}")
        resultAddPan.launch(intentAddNewPan)
    }
}