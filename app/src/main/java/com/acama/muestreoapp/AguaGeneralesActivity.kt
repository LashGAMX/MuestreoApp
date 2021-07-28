
 package com.acama.muestreoapp

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
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
     private  var idSol:Int = 0
     private var folio:String = ""
     private var sw1 = false
     private var datosMuestreo: MutableList<String> = mutableListOf()
     private var datosGenerales: MutableList<String> = mutableListOf()


     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         bin = ActivityAguaGeneralesBinding.inflate(layoutInflater)
         setContentView(bin.root)

         val context = this
         con = DataBaseHandler(this)
         val db: SQLiteDatabase = con.readableDatabase

         folio = intent.getStringExtra("folio").toString()
         Log.d("folio",folio)
         val qrSolGenModel = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
         val solGenModel = db.rawQuery(qrSolGenModel, null)

         if (solGenModel.moveToFirst()) {
             do {
                 datosMuestreo.add(solGenModel.getString(0))
                 datosMuestreo.add(solGenModel.getString(1))
                 datosMuestreo.add(solGenModel.getString(2))
                 datosMuestreo.add(solGenModel.getString(3))
                 datosMuestreo.add(solGenModel.getString(4))
                 datosMuestreo.add(solGenModel.getString(5))
                 datosMuestreo.add(solGenModel.getString(6))
                 datosMuestreo.add(solGenModel.getString(7))
                 datosMuestreo.add(solGenModel.getString(8))
                 datosMuestreo.add(solGenModel.getString(9))
                 datosMuestreo.add(solGenModel.getString(10))
                 datosMuestreo.add(solGenModel.getString(11))
                 datosMuestreo.add(solGenModel.getString(12))
                 datosMuestreo.add(solGenModel.getString(13))
                 datosMuestreo.add(solGenModel.getString(14))
                 datosMuestreo.add(solGenModel.getString(15))
                 datosMuestreo.add(solGenModel.getString(16))
             } while (solGenModel.moveToNext())
             // Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
         }
         Log.d("datosGenerales",datosMuestreo.toString())
         idSol = datosMuestreo[0].toInt()
         val qrGeneral = "SELECT * FROM campo_generales WHERE Id_solicitud = '$idSol'"
         val campoGeneral = db.rawQuery(qrGeneral, null)

         if (campoGeneral.moveToFirst()) {
             do {
                 datosGenerales.add(campoGeneral.getString(0))
                 datosGenerales.add(campoGeneral.getString(1))
                 datosGenerales.add(campoGeneral.getString(2))
                 datosGenerales.add(campoGeneral.getString(3))
                 datosGenerales.add(campoGeneral.getString(4))
                 datosGenerales.add(campoGeneral.getString(5))
                 datosGenerales.add(campoGeneral.getString(6))
                 datosGenerales.add(campoGeneral.getString(7))
                 datosGenerales.add(campoGeneral.getString(8))
                 datosGenerales.add(campoGeneral.getString(9))
             } while (campoGeneral.moveToNext())
             // Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
         }

         Log.d("Campo General",datosGenerales.toString())

         mostrarDatosGenerales()

         bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
             DialogVolver()
         })

         bin.btnPromPhT1.setOnClickListener {
             ValPhTrazable1()
         }
         bin.btnPromPhT2.setOnClickListener {
             ValPhTrazable2()
         }
         bin.btnProbarCalidad1.setOnClickListener {
             ValPhCalidad1()
         }
         bin.btnProbarCalidad2.setOnClickListener {
             ValPhCalidad2()
         }
         bin.btnProbarConductividad.setOnClickListener {
             ValConductividad()
         }
         bin.btnProbarConductividadCalidad.setOnClickListener {
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
     fun mostrarDatosGenerales(){
         //Datos generales
         bin.txtCliente.text = datosMuestreo[6]
         bin.txtFolioServicio.text = datosMuestreo[1]
         bin.txtNumTomas.text = datosMuestreo[14]
         bin.txtTipoDescarga.text = datosMuestreo[11]

         bin.edtLatitud.setText(datosGenerales[6])
         bin.edtLongitud.setText(datosGenerales[7])
     }

     fun generales() {
         var db = DataBaseHandler(this)
         //Insertar en tabla generales
         var generales = Generales(
             1,
             "Mobil",
             1,
             "10°C",
             "10°C",
             bin.edtLatitud.text.toString(),
             bin.edtLongitud.text.toString(),
             "15",
             "Criterio"
         )
         db.insertGeneral(generales)
     }

     fun phtrazable() {
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

     fun phcalidad() {
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

     fun conductividad() {
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
         val termos : MutableList<String> = ArrayList()
         val phTrazable : MutableList<String> = ArrayList()
         val phCalidad : MutableList<String> = ArrayList()
         val db: SQLiteDatabase = con.readableDatabase

         //val solGenModel = "SELECT * FROM campo_generales WHERE Id_solicitud = "

         val queryTerm = "SELECT * FROM TermometroCampo"
         //val termos = arrayOf("Termo 1", "Termo 2", "Termo 3", "Termo 4", "Termo 5")
         val termometroModel = db.rawQuery(queryTerm, null)
         var cont: Int = 0
         if (termometroModel.moveToFirst()) {
             do {
                 //listaMuestreo.add("" + muestreoModel.getString(1) + "\n" + muestreoModel.getString(6))
                     if (cont == 0){
                         termos.add(termometroModel.getString(2))
                     }
                 termos.add(termometroModel.getString(2))
                 cont++
             } while (termometroModel.moveToNext())
             // Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
         } else {
             // Toast.makeText(this,"Usuario y/o contraseña incorrecto",Toast.LENGTH_SHORT).show()
         }
         val adTermo = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, termos
         )
         bin.spnTermo.adapter = adTermo

         val qePhTazable = "SELECT * FROM cat_phTrazable"
         //val termos = arrayOf("Termo 1", "Termo 2", "Termo 3", "Termo 4", "Termo 5")
         val phTModel = db.rawQuery(qePhTazable, null)
         if (phTModel.moveToFirst()) {
             do {
                 //listaMuestreo.add("" + muestreoModel.getString(1) + "\n" + muestreoModel.getString(6))
                 phTrazable.add(phTModel.getString(1))
             } while (phTModel.moveToNext())
             // Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
         } else {
             // Toast.makeText(this,"Usuario y/o contraseña incorrecto",Toast.LENGTH_SHORT).show()
         }
         val adPhTrazable = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, phTrazable
         )

         val qePhCalidad = "SELECT * FROM cat_phCalidad"
         //val termos = arrayOf("Termo 1", "Termo 2", "Termo 3", "Termo 4", "Termo 5")
         val phCModel = db.rawQuery(qePhCalidad, null)
         if (phCModel.moveToFirst()) {
             do {
                 //listaMuestreo.add("" + muestreoModel.getString(1) + "\n" + muestreoModel.getString(6))
                 phCalidad.add(phCModel.getString(1))
             } while (phTModel.moveToNext())
             // Toast.makeText(this,"Sesión satisfactoria",Toast.LENGTH_SHORT).show()
         } else {
             // Toast.makeText(this,"Usuario y/o contraseña incorrecto",Toast.LENGTH_SHORT).show()
         }
         val adPhCalidad = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, phCalidad
         )

         //val phTrazable = arrayOf("Select", "7")

         /*val adPhTrazable = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, phTrazable
         )*/
         bin.spnPhTrazable.adapter = adPhTrazable
         bin.spnPhTrazable2.adapter = adPhTrazable
         bin.spnPhTrazableCalidad.adapter = adPhCalidad
         bin.spnPhTrazableCalidad2.adapter = adPhCalidad

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
         if (bin.phTrazable1.text.toString() == "" || bin.phTrazable2.text.toString() == "" || bin.phTrazable3.text.toString() == "") {
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
             if (dif1.equals(0) && dif2.equals(0) && dif3.equals(0)) {
                 bin.PromedioPhT1.setText("ACEPTADO")
             } else {
                 if (dif1 >= 3 || dif1 >= -3) {
                     if (dif2 >= 3 || dif2 >= -3) {
                         if (dif3 >= 3 || dif3 >= -3) {
                             bin.PromedioPhT1.setText("ACEPTADO")
                         }
                     }
                 } else {
                     bin.PromedioPhT1.setText("RECHAZADO")
                 }
             }
             //validacion sw1
             if (bin.PromedioPhT1.equals("ACEPTADO")){
                 sw1 = true
             }
         }
     }

     fun ValPhTrazable2() {
         //validacion campos vacios
         if (bin.ph2Trazable1.text.toString() == "" || bin.ph2Trazable2.text.toString() == "" || bin.ph2Trazable3.text.toString() == "") {
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
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(
                     PhTrazableSpn
                 )
             ) {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 0.03 || dif1 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Trazable1.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
                         .show()
                     bin.ph2Trazable1.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 0.03 || dif2 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Trazable2.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
                         .show()
                     bin.ph2Trazable2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 0.03 || dif3 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Trazable1.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
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
         if (bin.phCalidad1.text.toString() == "" || bin.phCalidad2.text.toString() == "" || bin.phCalidad3.text.toString() == "") {
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
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(
                     PhTrazableSpn
                 )
             ) {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 0.03 || dif1 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.phCalidad1.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
                         .show()
                     bin.phCalidad2.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 0.03 || dif2 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.phCalidad2.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
                         .show()
                     bin.phCalidad2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 0.03 || dif3 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.phCalidad1.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
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
         if (bin.ph2Calidad1.text.toString() == "" || bin.ph2Calidad2.text.toString() == "" || bin.ph2Calidad3.text.toString() == "") {
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
             if (global1.equals(PhTrazableSpn) || global2.equals(PhTrazableSpn) || global3.equals(
                     PhTrazableSpn
                 )
             ) {
                 Toast.makeText(applicationContext, "pH coincide", Toast.LENGTH_SHORT).show()
                 if (dif1 <= 0.03 || dif1 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Calidad1.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
                         .show()
                     bin.ph2Calidad2.setError("valor invalido entre 1 y 2")
                 }
                 if (dif2 >= 0.03 || dif2 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Calidad2.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
                         .show()
                     bin.ph2Calidad2.setError("valor invalido entre 1 y 3")
                 }
                 if (dif3 >= 0.03 || dif3 >= -0.03) {
                     Toast.makeText(applicationContext, "Valor aceptado", Toast.LENGTH_SHORT).show()
                     bin.ph2Calidad1.setError(null)
                 } else {
                     Toast.makeText(
                         applicationContext,
                         "Error: Verifica los datos",
                         Toast.LENGTH_LONG
                     )
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
         if (bin.conductividad1.text.toString() == "" || bin.conductividad2.text.toString() == "" || bin.conductividad3.text.toString() == "") {
             bin.PromedioPhT1.setError("Los campos están vacios") //error de campos vacios
         } else {
             bin.PromedioPhT1.setError(null)
             // Declaración d evariables para las comprobaciones y operaciones
             var uno = bin.conductividad1.text.toString().toInt()
             var dos = bin.conductividad2.text.toString().toInt()
             var tres = bin.conductividad3.text.toString().toInt()

             var dif1 = dos - uno
             var dif2 = dos - tres
             var dif3 = uno - tres

             Log.d("dif1", dif1.toString())
             Log.d("dif2", dif2.toString())
             Log.d("dif3", dif3.toString())


             var PhTrazableSpn = bin.spnConductividad.selectedItem.toString().toInt()

             //Validacion de tolerancia entre datos

             if (dif1 == 0 && dif2 == 0 && dif3 == 0) {
                 bin.PromedioConductividad.setText("ACEPTADO")
             } else {
                 if (dif1 >= 5 || dif1 >= -5) {
                     if (dif2 >= 5 || dif2 >= -5) {
                         if (dif3 >= 5 || dif3 >= -5) {
                             bin.PromedioConductividad.setText("ACEPTADO")
                         }
                     }
                 } else {
                     bin.PromedioConductividad.setText("RECHAZADO")
                 }
                 //Promedio

//                 val result = uno + dos + tres
//                 val prom = result / 3
//                 val promedio = BigDecimal(prom).setScale(2, RoundingMode.HALF_EVEN)
//                 //bin.PromedioConductividad.setText(promedio.toString())
//                 bin.PromedioConductividad!!.setEnabled(false)
             }
         }
     }

     fun ValConductiCalidad() {
         //validacion campos vacios
         if (bin.conducti1.text.toString() == "" || bin.conducti2.text.toString() == "" || bin.conducti3.text.toString() == "") {
             bin.PromedioConductividadCalidad.setError("Los campos están vacios") //error de campos vacios
         } else {
             bin.PromedioConductividadCalidad.setError(null)
             // Declaración d evariables para las comprobaciones y operaciones
             var uno = bin.conducti1.text.toString().toInt()
             var dos = bin.conducti2.text.toString().toInt()
             var tres = bin.conducti3.text.toString().toInt()

             var dif1 = dos - uno
             var dif2 = dos - tres
             var dif3 = uno - tres
             var PhTrazableSpn = bin.spnConductividadCalidad.selectedItem.toString().toInt()

             //Validacion de tolerancia entre datos
             if (dif1 == 0 && dif2 == 0 && dif3 == 0) {
                 bin.PromedioConductividad.setText("ACEPTADO")
             } else {
                 if (dif1 >= 5 || dif1 >= -5) {
                     if (dif2 >= 5 || dif2 >= -5) {
                         if (dif3 >= 5 || dif3 >= -5) {
                             bin.PromedioConductividad.setText("ACEPTADO")
                         }
                     }
                 } else {
                     bin.PromedioConductividad.setText("RECHAZADO")
                 }
                 //Promedio
                 val result = uno + dos + tres
                 val prom = result / 3
                 val promedio = BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN)
                 bin.PromedioConductividad.setText(promedio.toString())
                 bin.PromedioConductividad!!.setEnabled(false)
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
