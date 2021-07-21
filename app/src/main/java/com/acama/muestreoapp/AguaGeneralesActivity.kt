
 package com.acama.muestreoapp

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.green
import android.os.Bundle
import android.util.Half.round
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
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round
import kotlin.math.roundToInt

 class AguaGeneralesActivity : AppCompatActivity() {
     private lateinit var bin: ActivityAguaGeneralesBinding
     private lateinit var con: DataBaseHandler

     var sw = 0

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         bin = ActivityAguaGeneralesBinding.inflate(layoutInflater)
         setContentView(bin.root)

         val context = this

         bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
             DialogVolver()
         })

         bin.btnPromPhT1.setOnClickListener{
              ValPhTrazable1()
         }


         bin.btnGuardar.setOnClickListener {
            // CoroutineScope(Dispatchers.IO).launch {
               /* generales()
                phtrazable()
                 phcalidad()
             conductividad()*/
            // }
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

    fun ValPhTrazable1()  {
        //VALIDACION PH TRAZABLE
        var uno = bin.phTrazable1.text.toString().toDouble()
        var dos = bin.phTrazable2.text.toString().toDouble()
        var tres = bin.phTrazable3.text.toString().toDouble()

        var global1 = uno.toInt()
        var global2 = dos.toInt()
        var global3 = tres.toInt()
        var PhTrazableSpn = bin.spnPhTrazable.selectedItem.toString().toInt()

        Log.d("spiner", PhTrazableSpn.toString())
        var dif1: Double = dos - uno
        var dif2: Double = dos - tres
        var dif3: Double = uno - dos
        Log.d("dif1",dif1.toString())
        Log.d("dif2",dif2.toString())
        Log.d("dif3",dif3.toString())
        Log.d("global1", global1.toString())
        Log.d("global2", global2.toString())
        Log.d("global3", global3.toString())
        if (global1.equals(PhTrazableSpn)||global2.equals(PhTrazableSpn)||global3.equals(PhTrazableSpn))
        {
            Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
        }
        else{
            bin.PromedioPhT1.setError("El valor fuera de rango")

        }


        if (dif1 <= 0.03 || dif1 >= -0.03) {
            Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
            bin.phTrazable1.setError(null)
        } else {
            Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG).show()
            bin.phTrazable1.setError("valor invalido entre 1 y 2")
        }
        if (dif2 >= 0.03 || dif2 >= -0.03) {
            Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
            bin.phTrazable2.setError(null)
        } else {
            Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG).show()
            bin.phTrazable2.setError("valor invalido entre 1 y 3")
        }
        if (dif3 >= 0.03 || dif3 >= -0.03) {
            Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
            bin.phTrazable1.setError(null)
        } else {
            Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG).show()
            bin.phTrazable1.setError("valor invalido entre 1 y 2")
        }
        //Promedio
        val result = uno + dos + tres
        val promedio = BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
        bin.PromedioPhT1.setText(promedio.toString())
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
