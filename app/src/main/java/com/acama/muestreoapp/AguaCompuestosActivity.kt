package com.acama.muestreoapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.acama.muestreoapp.databinding.ActivityAguaCompuestosBinding



class AguaCompuestosActivity : AppCompatActivity() {
    private lateinit var bin: ActivityAguaCompuestosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaCompuestosBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            DialogVolver()
        })

        bin.btnGuardar.setOnClickListener { guardarDatos() }
        llenarSpinner()
    }
    fun guardarDatos() {
        val intent = Intent(this,AguaCapturaActivity::class.java)
        startActivity(intent)
    }
    fun llenarSpinner() {
        val aforos = arrayOf("Aforo 1","Aforo 2","Aforo 3")
        val adpAforo = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            aforos
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