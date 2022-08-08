package com.acama.muestreoapp

import android.R
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.acama.muestreoapp.agua.MuestraSimple
import com.acama.muestreoapp.agua.MuestraSimpleAdapter
import com.acama.muestreoapp.models.ObservacionGeneral
import com.acama.muestreoapp.databinding.ActivityAguaDatosMuestreosBinding

class AguaDatosMuestreosActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaDatosMuestreosBinding
    private  lateinit var folio:String
    private  lateinit var con: DataBaseHandler
    private var numTomas:Int = 0
    private var obsGuardada: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaDatosMuestreosBinding.inflate(layoutInflater)
        setContentView(bin.root)
        con = DataBaseHandler(this)
        getExtras()
        getNumTomas()
        bin.edtObservacion.setText(getObservacion()) // obtiene la observacion guardada

        folio = intent.getStringExtra("folio").toString()

        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            DialogVolver()
        })

        //GUARDADO DE OBSERVACIONES GENERALES DE LAS MUESTRAS
        bin.btnGuardar.setOnClickListener(){
            if(obsGuardada.isEmpty()){
                guardarObservacion()
                // Guarda la observación cuando no existe una ya por defecto
            } else {
                Toast.makeText(this, "Ya se registró una observación", Toast.LENGTH_SHORT).show()
            }

        }

    }
   fun guardarObservacion(){
          var obsModel = ObservacionGeneral(

              bin.edtObservacion.text.toString(),
              folio,
          )
          con.insertObsGeneral(obsModel)

      
   }
    fun getObservacion(): String {
        val db: SQLiteDatabase = con.readableDatabase
        val query = "SELECT * FROM observacion_general WHERE Folio = '$folio'"
        val model = db.rawQuery(query, null)
        if (model.getCount() > 0){
            model.moveToFirst()
            obsGuardada = model.getString(model.getColumnIndex("Observacion"))
        }
        model.close()
        return obsGuardada
    }

    fun getNumTomas(){
        var listaTomas: MutableList<MuestraSimple> = mutableListOf()
        val db : SQLiteDatabase = con.readableDatabase
        val query = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
        val model = db.rawQuery(query,null)
        if (model.moveToFirst()){
            do{
                numTomas = model.getInt(14)
            }while (model.moveToNext())
        }

        var cont:Int
        cont = numTomas
        for (i in 0 until numTomas){
            listaTomas.add(0,MuestraSimple(cont.toString(),2))
            cont--
        }


        bin.rvMuestraSimple.layoutManager = LinearLayoutManager(this)
        val adapter = MuestraSimpleAdapter(listaTomas,folio.toString())
        bin.rvMuestraSimple.adapter = adapter
    }


    fun getExtras(){
        val bundle = intent.extras
        val fol = bundle?.get("folio")
        folio = fol.toString()
        Log.d("Folio",folio)
    }

    fun DialogVolver(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cuidado")
        builder.setMessage("Los datos capturados se perderan ¿Seguro que quieres salir?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }
        builder.show()

    }

}

