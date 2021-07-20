
 package com.acama.muestreoapp

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.green
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acama.muestreoapp.databinding.ActivityAguaGeneralesBinding
import android.util.Log.d
import kotlinx.android.synthetic.main.activity_agua_generales.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

 class AguaGeneralesActivity : AppCompatActivity() {
     private lateinit var bin: ActivityAguaGeneralesBinding
     private lateinit var con: DataBaseHandler
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         bin = ActivityAguaGeneralesBinding.inflate(layoutInflater)
         setContentView(bin.root)

         val context = this

         bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
             DialogVolver()
         })


         bin.btnGuardar.setOnClickListener {
            // CoroutineScope(Dispatchers.IO).launch {
                generales()
                phtrazable()
                 phcalidad()
             conductividad()
            // }
             //Validaciones()
             //guardarDatos()
         }

         llenarSpinner()

     }
     fun generales(){
         var db = DataBaseHandler(this)
         //Insertar en tabla generales
         var generales = Generales(
             1,
             "Mobil",
             1,
             "10°C",
             "10°C",
             bin.latitud.text.toString(),
             bin.longitud.text.toString(),
             bin.altitud.text.toString(),
             "15",
             "Criterio"
         )
         db.insertGeneral(generales)
     }
     fun phtrazable(){
         var db = DataBaseHandler(this)
         //insertar en tabla PhTrasable
         val phTrazable = PhTrazable(
             1,
             1,
             bin.phTrazable1.text.toString(),
             bin.phTrazable2.text.toString(),
             bin.phTrazable3.text.toString(),
             "estado"
         )
         db.insertPhTrazable(phTrazable)
     }
     fun phcalidad(){
         var db = DataBaseHandler(this)
         //insertar en tabla PhCalidad
         val phCalidad = PhCalidad(
             1,
             1,
             bin.ph2Calidad1.text.toString(),
             bin.ph2Calidad2.text.toString(),
             bin.ph2Calidad3.text.toString(),
             "estado",
             "14.5"
         )
         db.insertPhCalidad(phCalidad)
     }
     fun conductividad(){
         var db = DataBaseHandler(this)
         val conductividad = Conductividad(
             1,
             bin.conductividad1.text.toString(),
             bin.conductividad2.text.toString(),
             bin.conductividad3.text.toString(),
             "3.15"
         )
     }

     fun guardarDatos() {
         val intent = Intent(this, AguaCapturaActivity::class.java)
         Toast.makeText(applicationContext, "Datos guardados", Toast.LENGTH_SHORT).show()
         startActivity(intent)
     }
     fun llenarSpinner() {
         val termos = arrayOf("Termo 1", "Termo 2", "Termo 3", "Termo 4", "Termo 5")
         val adTermo = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, termos
         )
         bin.spnTermo.adapter = adTermo

         val phTrazable = arrayOf("Select", "7")

         val adPhTrazable = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, phTrazable
         )
         bin.spnPhTrazable.adapter = adPhTrazable
         bin.spnPhTrazable2.adapter = adPhTrazable
         bin.spnPhTrazableCalidad.adapter = adPhTrazable
         bin.spnPhTrazableCalidad2.adapter = adPhTrazable

         val conductividad = arrayOf("1412", "1315")

         val adpConductividad = ArrayAdapter(
             this,
             R.layout.simple_spinner_item,
             conductividad
         )
         bin.spnConductividad.adapter = adpConductividad


     }

    fun Validaciones() {
        //VALIDACION PH TRAZABLE
        var PhTrazable1 = bin.phTrazable1.text.toString().toDouble()
        /* var global1 = bin.phTrazable1.text.toString().toInt()
        var global2 = bin.phTrazable2.text.toString().toInt()
        var global3 = bin.phTrazable3.text.toString().toInt()

        */
        var PhTrazable2 = bin.phTrazable2.text.toString().toDouble()
        val PhTrazable3 = bin.phTrazable3.text.toString().toDouble()
        // val PhTrazableSpn = bin.spnPhTrazable.selectedItem.toString().toInt()
        var dif1: Double = PhTrazable2 - PhTrazable1
        var dif2: Double = PhTrazable2 - PhTrazable3
        var dif3: Double = PhTrazable1 - PhTrazable2
        Log.d("dif1",dif1.toString())
        Log.d("dif2",dif2.toString())
        Log.d("dif3",dif3.toString())
        /* if (PhTrazableSpn.equals(global1) && PhTrazableSpn.equals(global2) && PhTrazableSpn.equals(global3)){
            Toast.makeText(applicationContext,"VALORES CORRECTOS", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(applicationContext,"VALOR DE PH FUERA DE RANGO", Toast.LENGTH_SHORT).show()
        }

        */


        if (dif1 <= 0.03 || dif1 >= -0.03) {
            Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG).show()
            bin.phTrazable1.setError("valor invalido entre 1 y 2")
        }
        if (dif2 >= 0.03 || dif2 >= -0.03) {
            Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG).show()
            bin.phTrazable2.setError("valor invalido entre 1 y 3")
        }
        if (dif3 >= 0.03 || dif3 >= -0.03) {
            Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG).show()
            bin.phTrazable1.setError("valor invalido entre 1 y 2")
        }


        //VALIDACION PH CALIDAD
    }

        fun DialogVolver() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cuidado")
            builder.setMessage("Los datos capturados se perderan ¿Seguro que quieres salir?")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(
                    applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT
                ).show()
                onBackPressed()
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(
                    applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()

        }
    }
