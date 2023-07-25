package com.acama.muestreoapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.acama.muestreoapp.databinding.ActivityAguaCompuestosBinding
import com.acama.muestreoapp.models.CampoCompuesto


class AguaCompuestosActivity : AppCompatActivity() {
    private lateinit var bin: ActivityAguaCompuestosBinding
    private lateinit var con: DataBaseHandler
    private lateinit var folio:String
    private var idSol:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaCompuestosBinding.inflate(layoutInflater)
        setContentView(bin.root)
        con = DataBaseHandler(this)
        val db: SQLiteDatabase = con.readableDatabase
        getExtras()
        val qrSolGenModel = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
        val solGenModel = db.rawQuery(qrSolGenModel, null)
        if (solGenModel.moveToFirst()) {
            do {
                idSol = solGenModel.getInt(0)
            } while (solGenModel.moveToNext())
        }


        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            DialogVolver()
        })

        bin.btnGuardarCompuestos.setOnClickListener {
            guardarDatos() }
        llenarSpinner()
    }
    fun guardarDatos() {
//        val intent = Intent(this,AguaCapturaActivity::class.java)
//        startActivity(intent)
        val dbw: SQLiteDatabase = con.writableDatabase
        var cv = ContentValues()

        cv.put("Metodo_aforo",bin.spnAforo.selectedItemId.toString())
        cv.put("Con_tratamiento",bin.spnConTratamiento.selectedItemId.toString())
        cv.put("Tipo_tratamiento",bin.spnTipoTratamiento.selectedItemId.toString())
        cv.put("Proc_muestreo",bin.edtProcedimiento.text.toString())
        cv.put("Observaciones",bin.edtObservaciones.text.toString())
        cv.put("Obser_solicitud","")
        cv.put("Ph_muestraComp",bin.edtPhCompuesto.text.toString())
        cv.put("Temp_muestraComp",bin.edtTempCompuesta.text.toString())
        cv.put("Volumen_calculado",bin.edtVolCalculado.text.toString())
        cv.put("Cloruros",bin.edtCloruros.text.toString())

        dbw.update("campo_compuesto", cv, "Id_solicitud = "+idSol, null)

         //onBackPressed()
        //guardar datos en table metodo Update

    }

    fun getExtras(){
        val bundle = intent.extras
        val fol = bundle?.get("folio")
        folio = fol.toString()
        Log.d("Folio",folio)
    }
    fun llenarSpinner() {
        val aforos : MutableList<String> = ArrayList()
        val tratamiento : MutableList<String> = ArrayList()
        val conTratamiento : MutableList<String> = ArrayList()
        val db: SQLiteDatabase = con.readableDatabase

        val queryAforo = "SELECT * FROM aforo"
        val aforoModel = db.rawQuery(queryAforo, null)
        if (aforoModel.moveToFirst()){
            aforos.add("Selecciona uno")
            do {
                aforos.add(aforoModel.getString(1))
            } while (aforoModel.moveToNext())
        }
        val adpAforo = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, aforos
        )

        val queryTratamiento = "SELECT * FROM tipo_tratamiento"
        val Modeltratamiento = db.rawQuery(queryTratamiento, null)
        if(Modeltratamiento.moveToFirst()){
            tratamiento.add("Selecciona uno")
            do {
                tratamiento.add(Modeltratamiento.getString(1))
            } while (Modeltratamiento.moveToNext())
        }
        val adpTratamiento = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, tratamiento
        )

        val queryConTratamiento =  "SELECT * FROM con_tratamiento"
        val conTratamientoModel = db.rawQuery(queryConTratamiento, null)
        if(conTratamientoModel.moveToFirst()){
            conTratamiento.add("Selecciona uno")
            do {
                conTratamiento.add(conTratamientoModel.getString(1))
            } while (conTratamientoModel.moveToNext())
        }
        val adpConTratamiento = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, conTratamiento
        )

        bin.spnTipoTratamiento.adapter = adpTratamiento
        bin.spnAforo.adapter = adpAforo
        bin.spnConTratamiento.adapter = adpConTratamiento


    }

    fun DialogVolver(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cuidado")
        builder.setMessage("Los datos capturados se perderan ¿Seguro que quieres salir?")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
                onBackPressed()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }

       // builder.setNeutralButton("Maybe") { dialog, which ->
            //Toast.makeText(applicationContext,
              //  "Maybe", Toast.LENGTH_SHORT).show()
       // }
        builder.show()

    }


}