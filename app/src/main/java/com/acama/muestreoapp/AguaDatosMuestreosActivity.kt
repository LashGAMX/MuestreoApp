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
import com.acama.muestreoapp.models.ConMuestra
import com.acama.muestreoapp.models.GastoMuestra
import com.acama.muestreoapp.models.PhCalidadMuestra
import com.acama.muestreoapp.models.PhMuestra
import com.acama.muestreoapp.models.TempAmbiente
import com.acama.muestreoapp.models.TempMuestra
import java.sql.DatabaseMetaData

class AguaDatosMuestreosActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaDatosMuestreosBinding
    private  lateinit var folio:String
    private  lateinit var con: DataBaseHandler
    private  var idSol:Int = 0
    private var numTomas:Int = 0
    private var obsGuardada: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaDatosMuestreosBinding.inflate(layoutInflater)
        setContentView(bin.root)
        con = DataBaseHandler(this)
        getExtras()
        getNumTomas()
        tablasIniciales()
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
                updateObservacion()
            }

        }

    }
    fun updateObservacion(){
        var obsUpdate = ObservacionGeneral(
            bin.edtObservacion.text.toString(),
            folio,

        )
        con.updateObsGeneral(obsUpdate)
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
    fun tablasIniciales() {
        val db: SQLiteDatabase = con.readableDatabase
        val toma = numTomas
        val qrSolGenModel = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
        val solGenModel = db.rawQuery(qrSolGenModel, null)
        if (solGenModel.moveToFirst()) {
            do {
                idSol = solGenModel.getInt(0)
            } while (solGenModel.moveToNext())
        }

            //ph_muestra
            val queryPhMuestra = "SELECT * FROM ph_muestra WHERE Id_solicitud = '$idSol'"
            val ph_muestraModel = db.rawQuery(queryPhMuestra, null)
            if (ph_muestraModel.moveToFirst()) {

            } else {
                for (i in 1..toma) {
                var phmuestraArray = PhMuestra(
                    idSol,
                    i,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                )
                var db = DataBaseHandler(this)
                db.insertPhMuestra(phmuestraArray)
                }

            }
        // temperatura muestra
        val queryTemperaturaMuestra = "SELECT * FROM temperatura_muestra WHERE Id_solicitud = '$idSol'"
        val temperatura_muestraModel = db.rawQuery(queryTemperaturaMuestra, null)
        if (temperatura_muestraModel.moveToFirst()) {

        } else {
            for (j in 1..toma){
            var tempMuestraArray = TempMuestra(
                idSol,
                j,
                "",
                "",
                "",
                "",
            )
            var db = DataBaseHandler(this)
            db.insertTempMuestra(tempMuestraArray)
            }

        }
            //temperatura ambiente
        val queryTemperaturaAmbiente = "SELECT * FROM temperatura_ambiente WHERE Id_solicitud = '$idSol'"
        val temperatura_ambienteModel = db.rawQuery(queryTemperaturaAmbiente, null)
        if (temperatura_ambienteModel.moveToFirst()){

        } else {
            for (k in 1 .. toma){
                var tempAmbienteArray = TempAmbiente(
                        idSol,
                    k,
                    "",
                    "",
                    "",
                    ""
                )
                var db = DataBaseHandler(this)
                db.insertTempAmbiente(tempAmbienteArray)
            }
        }
            //conductividad_muestra
        val queryConductividad = "SELECT * FROM conductividad_muestra WHERE Id_solicitud = '$idSol'"
        val conductividadModel = db.rawQuery(queryConductividad, null)
        if (conductividadModel.moveToFirst()){

        } else {
            for (c in 1 .. toma){
                var conductividadArray = ConMuestra(
                    idSol,
                    c,
                    "",
                    "",
                    "",
                    ""
                )
                var db = DataBaseHandler(this)
                db.insertConMuestra(conductividadArray)
            }
        }
            // gasto
        val queryGasto = "SELECT * FROM gasto_muestra WHERE Id_solicitud = '$idSol'"
        val gastoModel = db.rawQuery(queryGasto, null)
        if (gastoModel.moveToFirst()){

        } else {
            for (g in 1..toma) {
                var gastoArray = GastoMuestra(
                    idSol,
                    g,
                    "",
                    "",
                    "",
                    "",
                )
                var db = DataBaseHandler(this)
                db.insertGastoMuestra(gastoArray)
            }
        }
            // ph_calidadMuestra
        val queryPhCalMuestra = "SELECT * FROM ph_calidadMuestra WHERE Id_solicitud = '$idSol'"
        val phCalMuestraModel = db.rawQuery(queryPhCalMuestra, null)
        if (phCalMuestraModel.moveToFirst()){

        }else{
            for (p in 1 .. toma){
                var phCalMuestraArray = PhCalidadMuestra(
                    idSol,
                    p,
                    "",
                    "",
                    "",
                    "",
                    "",
                )
                var db = DataBaseHandler(this)
                db.insertPhCalidadMuestra(phCalMuestraArray)
            }

        }
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

