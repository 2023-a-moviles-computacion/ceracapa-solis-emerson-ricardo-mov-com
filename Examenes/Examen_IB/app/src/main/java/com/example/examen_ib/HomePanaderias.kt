package com.example.examen_ib


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class HomePanaderias :  AppCompatActivity() {

    var idSelectItem = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_panaderia)
        Log.i("ciclo-vida", "onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.i("ciclo-vida", "onStart")

        val listViewPanaderia = findViewById<ListView>(R.id.lv_panaderias_lista)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            PanaderiaBDD.TablaPanaderia!!.listarPanaderias()
        )
        listViewPanaderia.adapter = adaptador
        adaptador.notifyDataSetChanged()

        this.registerForContextMenu(listViewPanaderia)

        val btnAnadirPanaderia = findViewById<Button>(R.id.btn_crear_new_panaderia)
        btnAnadirPanaderia.setOnClickListener {
            val intentAddPanaderia = Intent(this, CrearPanaderia::class.java)
            startActivity(intentAddPanaderia)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // Guardar las variables
            // primitivos
            putInt("idItemSeleccionado",idSelectItem)
            putParcelableArrayList("arregloPanaderia",PanaderiaBDD.TablaPanaderia!!.listarPanaderias())
            putParcelableArrayList("arregloPP",Registers.arregloPanaderiasPanes)

        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        idSelectItem = savedInstanceState.getInt("idItemSeleccionado")

        Registers.arregloPanaderiasPanes = savedInstanceState.getParcelableArrayList<PanaderiasPanes>("arregloPP")!!
        if (idSelectItem == null){
            idSelectItem = 0
        }
        val listViewPanaderia = findViewById<ListView>(R.id.lv_panaderias_lista)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            PanaderiaBDD.TablaPanaderia!!.listarPanaderias()
        )
        listViewPanaderia.adapter = adaptador
        adaptador.notifyDataSetChanged()
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
        return when (item.itemId) {
            R.id.mi_editar -> {
                /*Log.i("context-menu", "Edit position: ${idSelectItem}")
                abrirActividadConParametros(EditarEquipo::class.java)*/
                return true
            }
            R.id.mi_eliminar -> {
                /*Log.i("context-menu", "Delete position: ${idSelectItem}")
                EquipoBaseDeDatos.TablaEquipo!!.eliminarEquipos(idSelectItem)
                val listViewEquipo = findViewById<ListView>(R.id.lv_equipos_lista)
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    EquipoBaseDeDatos.TablaEquipo!!.listarEquipos()
                )
                listViewEquipo.adapter = adaptador
                adaptador.notifyDataSetChanged()*/
                return true
            }
            R.id.mi_panes -> {
                /*Log.i("context-menu", "Jugadores: ${idSelectItem}")
                abrirActividadConParametros(InicioJugadores::class.java)*/
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ) {
        val intentEditarPanaderia = Intent(this, clase)
        intentEditarPanaderia.putExtra("posicionEditar", idSelectItem)
        startActivity(intentEditarPanaderia)
    }
}