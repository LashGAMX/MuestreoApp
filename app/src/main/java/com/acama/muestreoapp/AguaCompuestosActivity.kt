package com.acama.muestreoapp

import android.app.AlertDialog
import android.app.Dialog
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

        bin.btnGuardar.setOnClickListener { guardarDatos() }
        llenarSpinner()
    }
    fun guardarDatos() {
//        val intent = Intent(this,AguaCapturaActivity::class.java)
//        startActivity(intent)

        var cv = CampoCompuesto(
            idSol,
            bin.spnAforo.selectedItem.toString(),
            bin.spnConTratamiento.selectedItem.toString(),
            bin.spnTipoTratamiento.selectedItem.toString(),
            "",
            bin.edtObservaciones.text.toString(),
            "",
            bin.edtPhCompuesto.text.toString(),
            "",
        )
        con.insertCampoCompuesto(cv)
        onBackPressed()
    }

    fun getExtras(){
        val bundle = intent.extras
        val fol = bundle?.get("folio")
        folio = fol.toString()
        Log.d("Folio",folio)
    }
    fun llenarSpinner() {
        val aforos : MutableList<String> = ArrayList()
        val db: SQLiteDatabase = con.readableDatabase

        val queryAforo = "SELECT * FROM MetodoAforo"
        val aforoModel = db.rawQuery(queryAforo, null)
        if (aforoModel.moveToFirst()){
            do {
                aforos.add(aforoModel.getString(1))
            } while (aforoModel.moveToNext())
        }
        val adpAforo = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item, aforos
        )

        bin.spnAforo.adapter = adpAforo

        val conTratamiento = arrayOf("Tratamiento 1","Tratamiento 2","Tratamiento 3")
        val adpConTratamiento = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            conTratamiento
        )
        bin.spnConTratamiento.adapter = adpConTratamiento
        bin.spnTipoTratamiento.adapter = adpConTratamiento

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