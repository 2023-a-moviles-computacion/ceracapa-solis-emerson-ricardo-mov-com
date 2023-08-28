package com.example.examen_ib

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperPanaderiaPan(
    contexto: Context?,
): SQLiteOpenHelper(contexto, "moviles", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaPanaderia: ArrayList<String> = arrayListOf(
            """
               CREATE TABLE PAN(
               id INTEGER PRIMARY KEY,
               nombre VARCHAR(50),
               origen VARCHAR(50),
               EsDulce VARCHAR(50),
               Precio VARCHAR(50),
               Stock VARCHAR(50)
               );
             """, """
               CREATE TABLE PANADERIA(
               id INTEGER PRIMARY KEY,
               nombre VARCHAR(50),
               ubicacion VARCHAR(100),
               esCafeteria VARCHAR(50),
               arriendo VARCHAR(50),
               anio_fundacion VARCHAR(50)
               );
            """
        )
        for (i in scriptSQLCrearTablaPanaderia) {
            db!!.execSQL(i)
        }
        Log.i("creart", "Panaderias")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    // ------------------- PANADERIA -------------------
    fun crearPanaderia(id:Int, nombre:String, ubicacion:String, esCafeteria:String, arriendo:String, anio:String ): Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("id",id)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("ubicacion", ubicacion)
        valoresAGuardar.put("esCafeteria", esCafeteria)
        valoresAGuardar.put("arriendo", arriendo)
        valoresAGuardar.put("anio_fundacion", anio)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "PANADERIA",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun listarPanaderias(): ArrayList<Panaderia>{
        var lista = arrayListOf<Panaderia>()
        var panaderia: Panaderia?
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM PANADERIA"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        if(resultadoConsultaLectura.moveToFirst()){
            do {
                panaderia=Panaderia(0,"","","",0.0,0)
                panaderia!!.idPanaderia= resultadoConsultaLectura.getInt(0)
                panaderia.nombrePanaderia= resultadoConsultaLectura.getString(1)
                panaderia.ubicacionPanaderia= resultadoConsultaLectura.getString(2)
                panaderia.esCafeteria= resultadoConsultaLectura.getString(3)
                panaderia.arriendoPanaderia= resultadoConsultaLectura.getString(4).toDouble()
                panaderia.anioFundacion= resultadoConsultaLectura.getString(5).toInt()
                lista.add(panaderia)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return lista
    }

    fun actualizarPanaderia(id1:Int, nombre:String, ubicacion:String, esCafeteria:String, arriendo:String, anio:String ):Boolean{
        var lista= PanaderiaBDD.TablaPanaderia!!.listarPanaderias()
        val id=lista[id1].idPanaderia.toString()
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("ubicacion", ubicacion)
        valoresAActualizar.put("esCafeteria", esCafeteria)
        valoresAActualizar.put("arriendo", arriendo)
        valoresAActualizar.put("anio_fundacion", anio)
        val resultadoActualizacion = conexionEscritura
            .update(
                "PANADERIA", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    id.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun eliminarPanaderia(id:Int):Boolean{
        var lista= PanaderiaBDD.TablaPanaderia!!.listarPanaderias()
        val idE=lista[id].idPanaderia.toString()
        val conexion= writableDatabase
        val resultadoEliminacion=conexion
            .delete("PANADERIA","id=?", arrayOf(idE))
        conexion.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    // ------------------- PAN -------------------
    fun crearPan(id:Int, nombre:String, origen:String, esDulce: String, precio: String, stock: String):Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("id",id)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("origen", origen)
        valoresAGuardar.put("EsDulce", esDulce)
        valoresAGuardar.put("precio", precio)
        valoresAGuardar.put("stock", stock)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "PAN",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if(resultadoGuardar.toInt() == -1) false else true
    }

    fun listarPanes(): ArrayList<Pan>{
        var lista = arrayListOf<Pan>()
        var pan404: Pan?
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM PAN"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            null
        )
        if(resultadoConsultaLectura.moveToFirst()){
            do {
                pan404= Pan(0,"","","",0.0,0)
                pan404!!.idPan= resultadoConsultaLectura.getInt(0)
                pan404.nombrePan= resultadoConsultaLectura.getString(1)
                pan404.origenPan = resultadoConsultaLectura.getString(2)
                pan404.esDulce = resultadoConsultaLectura.getString(3)
                pan404.precioPan = resultadoConsultaLectura.getString(4).toDouble()
                pan404.stockPan = resultadoConsultaLectura.getString(5).toInt()
                lista.add(pan404)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return lista
    }

    fun actualizarPan(id:Int, nombre:String, origen:String, esDulce: String, precio: String, stock: String):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("origen", origen)
        valoresAActualizar.put("EsDulce", esDulce)
        valoresAActualizar.put("precio", precio )
        valoresAActualizar.put("stock", stock)
        val resultadoActualizacion = conexionEscritura
            .update(
                "PAN", // Nombre tabla
                valoresAActualizar,  // Valores a actualizar
                "id=?", // Clausula Where
                arrayOf(
                    id.toString()
                ) // Parametros clausula Where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun eliminarPan(id:Int):Boolean{
        val conexion= writableDatabase
        val resultadoEliminacion=conexion
            .delete("PAN","id=?", arrayOf(id.toString()))
        conexion.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }


}