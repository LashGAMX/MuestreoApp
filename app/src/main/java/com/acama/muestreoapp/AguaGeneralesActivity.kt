
 package com.acama.muestreoapp

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.acama.muestreoapp.databinding.ActivityAguaGeneralesBinding
import kotlinx.android.synthetic.main.activity_agua_generales.*
import java.math.BigDecimal
import java.math.RoundingMode


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
         bin.btnPromPhT2.setOnClickListener{
             ValPhTrazable2()
         }
         bin.btnProbarCalidad1.setOnClickListener{
             ValPhCalidad1()
         }
         bin.btnProbarCalidad2.setOnClickListener{
             ValPhCalidad2()
         }
         bin.btnProbarConductividad.setOnClickListener{
            ValConductividad()
         }
         bin.btnProbarConductividadCalidad.setOnClickListener{
             ValConductiCalidad()
         }


         bin.btnGuardar.setOnClickListener {
            // CoroutineScope(Dispatchers.IO).launch {
                generales()
                phtrazable()
                 phcalidad()
             conductividad()
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
//bloque 1
    fun ValPhTrazable1() {
    //validacion campos vacios
    if (bin.phTrazable1.text.toString() == "" || bin.phTrazable2.text.toString() == "" || bin.phTrazable3.text.toString() == "" )
    {
        bin.PromedioPhT1.setError("Los campos están vacios") //error de campos vacios
    } else {
        bin.PromedioPhT1.setError(null)
        // Declaración d evariables para las comprobaciones y operaciones
        var uno = bin.phTrazable1.text.toString().toDouble()
        var dos = bin.phTrazable2.text.toString().toDouble()
        var tres = bin.phTrazable3.text.toString().toDouble()

        var dif1: Double = dos - uno
        var dif2: Double = dos - tres
        var dif3: Double = uno - dos
        var global1 = uno.toInt()
        var global2 = dos.toInt()
        var global3 = tres.toInt()
        var PhTrazableSpn = bin.spnPhTrazable.selectedItem.toString().toInt()

        //Validacion de tolerancia entre datos
        if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(PhTrazableSpn))
        {
            Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
            if (dif1 <= 0.03 || dif1 >= -0.03) {
                Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                bin.phTrazable1.setError(null)
                bin.PromedioPhT1.setText("ACEPTADO")
            } else {
                Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                    .show()
                bin.phTrazable1.setError("valor invalido entre 1 y 2")
                bin.PromedioPhT1.setText("RECHAZADO")
            }
            if (dif2 >= 0.03 || dif2 >= -0.03) {
                Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                bin.phTrazable2.setError(null)
            } else {
                Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                    .show()
                bin.phTrazable2.setError("valor invalido entre 1 y 3")
            }
            if (dif3 >= 0.03 || dif3 >= -0.03) {
                Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                bin.phTrazable1.setError(null)
            } else {
                Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                    .show()
                bin.phTrazable1.setError("valor invalido entre 1 y 2")
            }
            //Promedio
            val result = uno + dos + tres
            val prom = result / 3
            val promedio = BigDecimal(prom).setScale(2, RoundingMode.HALF_EVEN)
            //(bin.PromedioPhT1.setText(promedio.toString())
            bin.PromedioPhT1!!.setEnabled(false)
        } else {
            bin.PromedioPhT1.setError("El valor fuera de rango")
        }
    }
}
     fun ValPhTrazable2() {
         //validacion campos vacios
         if (bin.ph2Trazable1.text.toString() == "" || bin.ph2Trazable2.text.toString() == "" || bin.ph2Trazable3.text.toString() == "" )
         {
             bin.PromedioPhT2.setError("Los campos están vacios") //error de campos vacios
         } else {
             bin.PromedioPhT2.setError(null)
             // Declaración d evariables para las comprobaciones y operaciones
             var uno = bin.ph2Trazable1.text.toString().toDouble()
             var dos = bin.ph2Trazable2.text.toString().toDouble()
             var tres = bin.ph2Trazable3.text.toString().toDouble()

             var dif1: Double = dos - uno
             var dif2: Double = dos - tres
             var dif3: Double = uno - dos
             var global1 = uno.toInt()
             var global2 = dos.toInt()
             var global3 = tres.toInt()
             var PhTrazableSpn = bin.spnPhTrazable2.selectedItem.toString().toInt()

             //Validacion de tolerancia entre datos
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(PhTrazableSpn))
             {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 0.03 || dif1 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Trazable1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.ph2Trazable1.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 0.03 || dif2 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Trazable2.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.ph2Trazable2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 0.03 || dif3 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Trazable1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.ph2Trazable1.setError("valor invalido entre 1 y 2")
                 }
                 //Promedio
                 val result = uno + dos + tres
                 val prom = result / 3
                 val promedio = BigDecimal(prom).setScale(2, RoundingMode.HALF_EVEN)
                 bin.PromedioPhT2.setText(promedio.toString())
                 bin.PromedioPhT2!!.setEnabled(false)
             } else {
                 bin.PromedioPhT2.setError("El valor fuera de rango")
             }
         }
     }
     // bloque 2 Calidad
     fun ValPhCalidad1() {
         //validacion campos vacios
         if (bin.phCalidad1.text.toString() == "" || bin.phCalidad2.text.toString() == "" || bin.phCalidad3.text.toString() == "" )
         {
             bin.PromedioCalidad.setError("Los campos están vacios") //error de campos vacios
         } else {
             bin.PromedioCalidad.setError(null)
             // Declaración d evariables para las comprobaciones y operaciones
             var uno = bin.phCalidad1.text.toString().toDouble()
             var dos = bin.phCalidad2.text.toString().toDouble()
             var tres = bin.phCalidad3.text.toString().toDouble()

             var dif1: Double = dos - uno
             var dif2: Double = dos - tres
             var dif3: Double = uno - dos
             var global1 = uno.toInt()
             var global2 = dos.toInt()
             var global3 = tres.toInt()
             var PhTrazableSpn = bin.spnPhTrazableCalidad.selectedItem.toString().toInt()

             //Validacion de tolerancia entre datos
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(PhTrazableSpn))
             {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 0.03 || dif1 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.phCalidad1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.phCalidad2.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 0.03 || dif2 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.phCalidad2.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.phCalidad2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 0.03 || dif3 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.phCalidad1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.phCalidad1.setError("valor invalido entre 1 y 2")
                 }
                 //Promedio
                 val result = uno + dos + tres
                 val prom = result / 3
                 val promedio = BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
                 bin.PromedioCalidad.setText(promedio.toString())
                 bin.PromedioCalidad!!.setEnabled(false)
             } else {
                 bin.PromedioCalidad.setError("El valor fuera de rango")
             }
         }
     }
     fun ValPhCalidad2() {
         //validacion campos vacios
         if (bin.ph2Calidad1.text.toString() == "" || bin.ph2Calidad2.text.toString() == "" || bin.ph2Calidad3.text.toString() == "" )
         {
             bin.PromedioCalidad2.setError("Los campos están vacios") //error de campos vacios
         } else {
             bin.PromedioCalidad2.setError(null)
             // Declaración d evariables para las comprobaciones y operaciones
             var uno = bin.ph2Calidad1.text.toString().toDouble()
             var dos = bin.ph2Calidad2.text.toString().toDouble()
             var tres = bin.ph2Calidad3.text.toString().toDouble()

             var dif1: Double = dos - uno
             var dif2: Double = dos - tres
             var dif3: Double = uno - dos
             var global1 = uno.toInt()
             var global2 = dos.toInt()
             var global3 = tres.toInt()
             var PhTrazableSpn = bin.spnPhTrazableCalidad2.selectedItem.toString().toInt()

             //Validacion de tolerancia entre datos
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(PhTrazableSpn))
             {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 0.03 || dif1 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Calidad1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.ph2Calidad2.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 0.03 || dif2 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Calidad2.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.ph2Calidad2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 0.03 || dif3 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Calidad1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.ph2Calidad1.setError("valor invalido entre 1 y 2")
                 }
                 //Promedio
                 val result = uno + dos + tres
                 val prom = result / 3
                 val promedio = BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
                 bin.PromedioCalidad2.setText(promedio.toString())
                 bin.PromedioCalidad2!!.setEnabled(false)
             } else {
                 bin.PromedioCalidad2.setError("El valor fuera de rango")
             }
         }
     }     //bloque 3 conductividad
     fun ValConductividad() {
         //validacion campos vacios
         if (bin.conductividad1.text.toString() == "" || bin.conductividad2.text.toString() == "" || bin.conductividad3.text.toString() == "" )
         {
             bin.PromedioPhT1.setError("Los campos están vacios") //error de campos vacios
         } else {
             bin.PromedioPhT1.setError(null)
             // Declaración d evariables para las comprobaciones y operaciones
             var uno = bin.conductividad1.text.toString().toInt()
             var dos = bin.conductividad2.text.toString().toInt()
             var tres = bin.conductividad3.text.toString().toInt()

             var dif1  = dos - uno
             var dif2  = dos - tres
             var dif3  = uno - dos
             var global1 = uno.toInt()
             var global2 = dos.toInt()
             var global3 = tres.toInt()
             var PhTrazableSpn = bin.spnPhTrazable.selectedItem.toString().toInt()

             //Validacion de tolerancia entre datos
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(PhTrazableSpn))
             {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 5 || dif1 >= -5) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.conductividad1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.conductividad1.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 5 || dif2 >= -5) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.conductividad2.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.conductividad2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 5 || dif3 >= -5) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.conductividad1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.conductividad1.setError("valor invalido entre 1 y 2")
                 }
                 //Promedio
                 val result = uno + dos + tres
                 val prom = result / 3
                 val promedio = BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
                 bin.PromedioConductividad.setText(promedio.toString())
                 bin.PromedioConductividad!!.setEnabled(false)
             } else {
                 bin.PromedioConductividad.setError("El valor fuera de rango")
             }
         }
     }
     fun ValConductiCalidad() {
         //validacion campos vacios
         if (bin.conducti1.text.toString() == "" || bin.conducti2.text.toString() == "" || bin.conducti3.text.toString() == "" )
         {
             bin.PromedioConductividadCalidad.setError("Los campos están vacios") //error de campos vacios
         } else {
             bin.PromedioConductividadCalidad.setError(null)
             // Declaración d evariables para las comprobaciones y operaciones
             var uno = bin.conducti1.text.toString().toDouble()
             var dos = bin.conducti2.text.toString().toDouble()
             var tres = bin.conducti3.text.toString().toDouble()

             var dif1: Double = dos - uno
             var dif2: Double = dos - tres
             var dif3: Double = uno - dos
             var global1 = uno.toInt()
             var global2 = dos.toInt()
             var global3 = tres.toInt()
             var PhTrazableSpn = bin.spnConductividadCalidad.selectedItem.toString().toInt()

             //Validacion de tolerancia entre datos
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(PhTrazableSpn))
             {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 0.03 || dif1 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.conducti1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.conducti1.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 0.03 || dif2 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.conducti2.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.conducti2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 0.03 || dif3 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.conducti1.setError(null)
                 } else {
                     Toast.makeText(applicationContext, "Error: Verifica los datos", Toast.LENGTH_LONG)
                         .show()
                     bin.conducti1.setError("valor invalido entre 1 y 2")
                 }
                 //Promedio
                 val result = uno + dos + tres
                 val prom = result / 3
                 val promedio = BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
                 bin.PromedioConductividad.setText(promedio.toString())
                 bin.PromedioConductividad!!.setEnabled(false)
             } else {
                 bin.PromedioConductividad.setError("El valor fuera de rango")
             }
         }
     }
     //Funcion para salir de la activity
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
