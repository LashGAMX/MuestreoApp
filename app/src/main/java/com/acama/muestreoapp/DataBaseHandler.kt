package com.acama.muestreoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


val DATABASE_NAME = "Muestreo"
val DATOSGENERALES = "campo_generales"
val Id_general = "Id_general"
val Id_solicitud = "Id_solicitud"
val Captura = "Captura"
val Id_equipo = "Id_equipo"
val Temperatura_a = "Temperatura_a"
val Temperatura_b = "Temperatura_b"
val Latitud = "Latitud"
val Longitud = "Longitud"
val Altitud = "Altitud"
val Pendiente = "Pendiente"
val Criterio = "Criterio"

//ph trazbale
val Id_solicitudT = "Id_solicitud"
val Id_phTrazable = "Id_phTrazable"
val Lectura1 = "Lectura1"
val Lectura2 = "Lectura2"
val Lectura3 = "Lectura3"
val Estado = "Estado"

class DataBaseHandler (var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val campo_general = "CREATE TABLE " + DATOSGENERALES + " (" +
                Id_general + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Id_solicitud + " INTEGER," +
                Captura + " VARCHAR(256)," +
                Id_equipo + " INTEGER," +
                Temperatura_a + " VARCHAR(256)," +
                Temperatura_b + " VARCHAR(256)," +
                Latitud + " VARCHAR(256)," +
                Longitud + " VARCHAR(256)," +
                Altitud + " VARCHAR(256)," +
                Pendiente + " VARCHAR(256)," +
                Criterio + " VARCHAR(256))";

        db?.execSQL(campo_general)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun insertGeneral(generales: Generales) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(Id_solicitud,generales.Id_solicitud)
        cv.put(Captura, generales.Captura)
        cv.put(Id_equipo, generales.Id_equipo)
        cv.put(Temperatura_a, generales.Temperatura_a)
        cv.put(Temperatura_b, generales.Temperatura_b)
        cv.put(Latitud, generales.Latitud)
        cv.put(Longitud, generales.Longitud)
        cv.put(Altitud, generales.Altitud)
        cv.put(Pendiente, generales.Pendiente)
        cv.put(Criterio, generales.Criterio)
        var result = db.insert(DATOSGENERALES, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }

    }
    fun insertPhTrazable(phTrazable: PhTrazable) {
        val db = this.writableDatabase
        var cv = ContentValues()
    //put datos
        cv.put(Id_solicitudT,phTrazable.Id_solicitud)
        cv.put(Id_phTrazable, phTrazable.Id_phTrazable)
        cv.put(Lectura1, phTrazable.Lectura1)
        cv.put(Lectura2, phTrazable.Lectura2)
        cv.put(Lectura3, phTrazable.Lectura3)
        cv.put(Estado, phTrazable.Estado)

        var result = db.insert(DATOSGENERALES, null,cv)
        if( result == -1.toLong())
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }
}
