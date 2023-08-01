
 package com.acama.muestreoapp

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.acama.muestreoapp.api.VolleySingleton
import com.acama.muestreoapp.databinding.ActivityAguaGeneralesBinding
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_agua_generales.*


 class AguaGeneralesActivity : AppCompatActivity() {
     private lateinit var bin: ActivityAguaGeneralesBinding
     private lateinit var con: DataBaseHandler
     lateinit var mFusedLocationClient: FusedLocationProviderClient
     val PERMISSION_ID = 42

     private  var idSol:Int = 0
     private var folio:String = ""

     private var sw1 = false
     private var sw2 = false
     private var sw3 = false
     private var sw4 = false
     private var sw5 = false
     private var sw6 = false
     private var sw7 = false


     private var datosMuestreo: MutableList<String> = mutableListOf()
     private var datosGenerales: MutableList<String> = mutableListOf()
     private var datosPhTrazable1: MutableList<String> = mutableListOf()
     private var datosPhTrazable2: MutableList<String> = mutableListOf()
     private var datosPhCalidad1: MutableList<String> = mutableListOf()
     private var datosPhCalidad2: MutableList<String> = mutableListOf()
     private var datosConTrazable: MutableList<String> = mutableListOf()
     private var datosConCalidad: MutableList<String> = mutableListOf()



     @SuppressLint("MissingPermission")
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
                 datosMuestreo.add(solGenModel.getString(17))
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
                 datosGenerales.add(campoGeneral.getString(10))
                 datosGenerales.add(campoGeneral.getString(11))
             } while (campoGeneral.moveToNext())
         }
         val qrTrazable = "SELECT * FROM ph_trazable WHERE Id_solicitud = '$idSol'"
         val phTrazableModel = db.rawQuery(qrTrazable, null)

         var cont = 0
         if (phTrazableModel.moveToFirst()) {
             do {
                 if (cont == 0){
                     datosPhTrazable1.add(phTrazableModel.getString(0))
                     datosPhTrazable1.add(phTrazableModel.getString(1))
                     datosPhTrazable1.add(phTrazableModel.getString(2))
                     datosPhTrazable1.add(phTrazableModel.getString(3))
                     datosPhTrazable1.add(phTrazableModel.getString(4))
                     datosPhTrazable1.add(phTrazableModel.getString(5))
                     datosPhTrazable1.add(phTrazableModel.getString(6))

                 }else{
                     datosPhTrazable2.add(phTrazableModel.getString(0))
                     datosPhTrazable2.add(phTrazableModel.getString(1))
                     datosPhTrazable2.add(phTrazableModel.getString(2))
                     datosPhTrazable2.add(phTrazableModel.getString(3))
                     datosPhTrazable2.add(phTrazableModel.getString(4))
                     datosPhTrazable2.add(phTrazableModel.getString(5))
                     datosPhTrazable2.add(phTrazableModel.getString(6))
                 }
                 cont++
             } while (phTrazableModel.moveToNext())
         }

         val qrPhCalidad = "SELECT * FROM ph_calidad WHERE Id_solicitud = '$idSol'"
         val phCalidadModel = db.rawQuery(qrPhCalidad, null)
         cont = 0
         if (phCalidadModel.moveToFirst()) {
             do {
                 if (cont == 0){
                     datosPhCalidad1.add(phCalidadModel.getString(0))
                     datosPhCalidad1.add(phCalidadModel.getString(1))
                     datosPhCalidad1.add(phCalidadModel.getString(2))
                     datosPhCalidad1.add(phCalidadModel.getString(3))
                     datosPhCalidad1.add(phCalidadModel.getString(4))
                     datosPhCalidad1.add(phCalidadModel.getString(5))
                     datosPhCalidad1.add(phCalidadModel.getString(6))
                     datosPhCalidad1.add(phCalidadModel.getString(7))

                 }else{
                     datosPhCalidad2.add(phCalidadModel.getString(0))
                     datosPhCalidad2.add(phCalidadModel.getString(1))
                     datosPhCalidad2.add(phCalidadModel.getString(2))
                     datosPhCalidad2.add(phCalidadModel.getString(3))
                     datosPhCalidad2.add(phCalidadModel.getString(4))
                     datosPhCalidad2.add(phCalidadModel.getString(5))
                     datosPhCalidad2.add(phCalidadModel.getString(6))
                     datosPhCalidad2.add(phCalidadModel.getString(7))
                 }
                 cont++
             } while (phCalidadModel.moveToNext())
         }

         val qrConTrazable = "SELECT * FROM con_trazable WHERE Id_solicitud = '$idSol'"
         val conCalidadTrazableModel = db.rawQuery(qrConTrazable, null)
         if (conCalidadTrazableModel.moveToFirst()) {
             do {
                     datosConTrazable.add(conCalidadTrazableModel.getString(0))
                 datosConTrazable.add(conCalidadTrazableModel.getString(1))
                 datosConTrazable.add(conCalidadTrazableModel.getString(2))
                 datosConTrazable.add(conCalidadTrazableModel.getString(3))
                 datosConTrazable.add(conCalidadTrazableModel.getString(4))
                 datosConTrazable.add(conCalidadTrazableModel.getString(5))
                 datosConTrazable.add(conCalidadTrazableModel.getString(6))

             } while (conCalidadTrazableModel.moveToNext())
         }

         val qrConCalidad = "SELECT * FROM con_calidad WHERE Id_solicitud = '$idSol'"
         val conCalidadModel = db.rawQuery(qrConCalidad, null)
         if (conCalidadModel.moveToFirst()) {
             do {
                 datosConCalidad.add(conCalidadModel.getString(0))
                 datosConCalidad.add(conCalidadModel.getString(1))
                 datosConCalidad.add(conCalidadModel.getString(2))
                 datosConCalidad.add(conCalidadModel.getString(3))
                 datosConCalidad.add(conCalidadModel.getString(4))
                 datosConCalidad.add(conCalidadModel.getString(5))
                 datosConCalidad.add(conCalidadModel.getString(6))
                 datosConCalidad.add(conCalidadModel.getString(7))

             } while (conCalidadModel.moveToNext())
         }


         Log.d("Campo General",datosGenerales.toString())

         llenarSpinner()
         //----Sincronizacíon de datos guardados----
         mostrarDatosGenerales()

         bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
             DialogVolver()
         })



         bin.btnPromPhT1.setOnClickListener {
             if (valPhTrazable(bin.spnPhTrazable,bin.phTrazable1,bin.phTrazable2,bin.phTrazable3,bin.phEstado1)){
                 Log.d("Val","Fue aceptado")
                 sw1 = true
             }else{
                 Log.d("Val","No Fue aceptado")
                 sw1 = false
             }
         }
         bin.btnPromPhT2.setOnClickListener {
             if (valPhTrazable(bin.spnPhTrazable2,bin.ph2Trazable1,bin.ph2Trazable2,bin.ph2Trazable3,bin.phEstado2)){
                 Log.d("Val","Fue aceptado")
                 sw2 = true
             }else{
                 Log.d("Val","No Fue aceptado")
                 sw2 = false
             }
         }
         bin.btnProbarCalidad1.setOnClickListener {
             if (valPhCalidad(bin.spnPhTrazableCalidad,bin.phCalidad1,bin.phCalidad2,bin.phCalidad3,bin.promCalidad1)){
                 Log.d("Val","Fue aceptado")
                 sw3 = true
             }else{
                 Log.d("Val","No Fue aceptado")
                 sw3 = false
             }
         }
         bin.btnProbarCalidad2.setOnClickListener {
             if (valPhCalidad(bin.spnPhTrazableCalidad2,bin.ph2Trazable1,bin.ph2Trazable2,bin.ph2Trazable3,bin.promedioCalidad2)){
                 Log.d("Val","Fue aceptado")
                 sw4 = true
             }else{
                 Log.d("Val","No Fue aceptado")
                 sw4 = false
             }
         }
         bin.btnProbarConductividad.setOnClickListener {
             if (valConTrazable(bin.spnConductividad,bin.edtCon1Tra,bin.edtCon2Tra,bin.edtCon3Tra,bin.edtEstadoConTra)){
                 Log.d("Val","Fue aceptado")
                 sw5 = true
             }else{
                 Log.d("Val","No Fue aceptado")
                 sw5 = false
             }

         }
         bin.btnProbarConductividadCalidad.setOnClickListener {
             if (valConCalidad(bin.spnConductividadCalidad,bin.edtCon1Cal,bin.edtCon2Cal,bin.edtCon3Cal,bin.PromedioConductividadCalidad)){
                 Log.d("Val","Fue aceptado")
                 sw6 = true
             }else{
                 Log.d("Val","No Fue aceptado")
                 sw6 = false
             }
         }
         bin.btnPendiente.setOnClickListener {
             try {
                 when (bin.edtPendiente.text.toString().toFloat()) {
                     in 94.0..105.0 -> sw7 = true
                     else -> {
                         bin.edtPendiente.setError("La pendiente supera el valor permitido")
                         sw7 = false
                     }
                 }
             } catch (e: Exception) {
                 Toast.makeText(this, "Error en pendiente", Toast.LENGTH_SHORT).show()
             }
         }
         bin.ImgCoordenadas.setOnClickListener{
            if (allPermissionGrantedGPS()){
                mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                ubicacionActual()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
            }

         }

         bin.btnGuardar.setOnClickListener {
            if (sw1 == true && sw2 == true && sw3 == true && sw4 == true && sw5 == true && sw6 == true && sw7 == true){
                var idPhTrazable : MutableList<String> = ArrayList()
                var idPhCalidad : MutableList<String> = ArrayList()
                var idConTrazable : MutableList<String> = ArrayList()
                var idConCalidad : MutableList<String> = ArrayList()

                val dbw: SQLiteDatabase = con.writableDatabase
                val dbr: SQLiteDatabase = con.readableDatabase

                val cv = ContentValues()
                cv.put("Captura","Movil")
                cv.put("Id_equipo",bin.spnTermo.selectedItem.toString())
                cv.put("Id_equipo2",bin.spnTermo2.selectedItem.toString())
                cv.put("Temperatura_a",bin.edtTemperatura.text.toString())
                cv.put("Temperatura_b",bin.edtTemperaturaBuff.text.toString())
                cv.put("Latitud",bin.edtLatitud.text.toString())
                cv.put("Longitud",bin.edtLongitud.text.toString())
                cv.put("Pendiente",bin.edtPendiente.text.toString())
                cv.put("Criterio","ACEPTADO")
                cv.put("Supervisor", bin.edtSuperviso.text.toString())
                dbw.update("campo_generales",cv,"Id_solicitud = "+idSol,null)

                val cvp = ContentValues()
                cvp.put("Punto",bin.edtPunto.text.toString())
                dbw.update("solicitud_generadas",cvp,"Id_solicitudGen = "+idSol,null)


                val qrPhTra = "SELECT * FROM ph_trazable WHERE Id_solicitud = '$idSol'"
                val phTraModel = dbr.rawQuery(qrPhTra, null)
                if (phTraModel.moveToFirst()) {
                    do {
                        idPhTrazable.add(phTraModel.getString(0))
                    } while (phTraModel.moveToNext())
                }

                val qrPhCal = "SELECT * FROM ph_calidad WHERE Id_solicitud = '$idSol'"
                val phCalModel = dbr.rawQuery(qrPhCal, null)
                if (phCalModel.moveToFirst()) {
                    do {
                        idPhCalidad.add(phCalModel.getString(0))
                    } while (phCalModel.moveToNext())
                }

                val qrConTra = "SELECT * FROM con_trazable WHERE Id_solicitud = '$idSol'"
                val conTraModel = dbr.rawQuery(qrConTra, null)
                if (conTraModel.moveToFirst()) {
                    do {
                        idConTrazable.add(conTraModel.getString(0))
                    } while (conTraModel.moveToNext())
                }
                val qrConCal = "SELECT * FROM con_calidad WHERE Id_solicitud = '$idSol'"
                val conCalModel = dbr.rawQuery(qrConCal, null)
                if (conCalModel.moveToFirst()) {
                    do {
                        idConCalidad.add(conCalModel.getString(0))
                    } while (conCalModel.moveToNext())
                }

                //Updata Ph Trazable
                val cv2 = ContentValues()
                cv2.put("Id_phTrazable",bin.spnPhTrazable.selectedItem.toString())
                cv2.put("Lectura1",bin.phTrazable1.text.toString())
                cv2.put("Lectura2",bin.phTrazable2.text.toString())
                cv2.put("Lectura3",bin.phTrazable3.text.toString())
                cv2.put("Estado",bin.phEstado1.text.toString())
                dbw.update("ph_trazable",cv2,"Id_ph ="+idPhTrazable[0],null)
                val cv3 = ContentValues()
                cv3.put("Id_phTrazable",bin.spnPhTrazable2.selectedItem.toString())
                cv3.put("Lectura1",bin.ph2Trazable1.text.toString())
                cv3.put("Lectura2",bin.ph2Trazable2.text.toString())
                cv3.put("Lectura3",bin.ph2Trazable3.text.toString())
                cv3.put("Estado",bin.phEstado2.text.toString())
                dbw.update("ph_trazable",cv3,"Id_ph ="+idPhTrazable[1].toString(),null)

                //Updata Ph Calidad
                val cv4 = ContentValues()
                cv4.put("Ph_calidad",bin.spnPhTrazable.selectedItem.toString())
                cv4.put("Lectura1",bin.phCalidad1.text.toString())
                cv4.put("Lectura2",bin.phCalidad2.text.toString())
                cv4.put("Lectura3",bin.phCalidad3.text.toString())
                cv4.put("Estado","ACEPTADO")
                cv4.put("Promedio",bin.promCalidad1.text.toString())
                dbw.update("ph_calidad",cv4,"Id_phCalidad ="+idPhCalidad[0],null)
                val cv5 = ContentValues()
                cv5.put("Ph_calidad",bin.spnPhTrazable2.selectedItem.toString())
                cv5.put("Lectura1",bin.ph2Calidad1.text.toString())
                cv5.put("Lectura2",bin.ph2Calidad2.text.toString())
                cv5.put("Lectura3",bin.ph2Calidad3.text.toString())
                cv5.put("Estado","ACEPTADO")
                cv5.put("Promedio",bin.promedioCalidad2.text.toString())
                dbw.update("ph_calidad",cv5,"Id_phCalidad ="+idPhCalidad[1].toString(),null)

                //Updata Conductividad Trazable
                val cv6 = ContentValues()
                cv6.put("Id_conTrazable",bin.spnConductividad.selectedItem.toString())
                cv6.put("Lectura1",bin.edtCon1Tra.text.toString())
                cv6.put("Lectura2",bin.edtCon2Tra.text.toString())
                cv6.put("Lectura3",bin.edtCon3Tra.text.toString())
                cv6.put("Estado","ACEPTADO")
                dbw.update("con_trazable",cv6,"Id_conductividad ="+idConTrazable[0],null)
                val cv7 = ContentValues()
                cv7.put("Id_conCalidad",bin.spnConductividadCalidad.selectedItem.toString())
                cv7.put("Lectura1",bin.edtCon1Cal.text.toString())
                cv7.put("Lectura2",bin.edtCon2Cal.text.toString())
                cv7.put("Lectura3",bin.edtCon3Cal.text.toString())
                cv7.put("Estado", "ACEPTADO")
                cv7.put("Promedio",bin.PromedioConductividadCalidad.text.toString())
                dbw.update("con_calidad",cv7,"Id_conductividad ="+idConCalidad[0],null)

                onBackPressed()
                //Toast.makeText(this,"Datos guardados",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"No puedes guardaros los registros antes de capturar",Toast.LENGTH_SHORT).show()
            }
         }



     }
     fun mostrarDatosGenerales(){
         //Datos generales
         bin.txtCliente.text = datosMuestreo[6]
         bin.txtFolioServicio.text = datosMuestreo[1]
         bin.txtNumTomas.text = datosMuestreo[14]
         bin.txtTipoDescarga.text = datosMuestreo[11]
         bin.edtPunto.setText(datosMuestreo[17])

         bin.edtTemperatura.setText(datosGenerales[5])
         bin.edtTemperaturaBuff.setText(datosGenerales[6])

         bin.edtLatitud.setText(datosGenerales[7])
         bin.edtLongitud.setText(datosGenerales[8])
         bin.edtSuperviso.setText(datosGenerales[11])
         bin.edtPendiente.setText(datosGenerales[9])

         bin.phTrazable1.setText(datosPhTrazable1[3])
         bin.phTrazable2.setText(datosPhTrazable1[4])
         bin.phTrazable3.setText(datosPhTrazable1[5])
         bin.phEstado1.setText(datosPhTrazable1[6])

         bin.ph2Trazable1.setText(datosPhTrazable2[3])
         bin.ph2Trazable2.setText(datosPhTrazable2[4])
         bin.ph2Trazable3.setText(datosPhTrazable2[5])
         bin.phEstado2.setText(datosPhTrazable2[6])

         bin.phCalidad1.setText(datosPhCalidad1[3])
         bin.phCalidad2.setText(datosPhCalidad1[4])
         bin.phCalidad3.setText(datosPhCalidad1[5])
         bin.promCalidad1.setText(datosPhCalidad1[7])

         bin.ph2Calidad1.setText(datosPhCalidad2[3])
         bin.ph2Calidad2.setText(datosPhCalidad2[4])
         bin.ph2Calidad3.setText(datosPhCalidad2[5])
         bin.promedioCalidad2.setText(datosPhCalidad2[7])

         bin.edtCon1Tra.setText(datosConTrazable[3])
         bin.edtCon2Tra.setText(datosConTrazable[4])
         bin.edtCon3Tra.setText(datosConTrazable[5])
         bin.edtEstadoConTra.setText(datosConTrazable[6])

         bin.edtCon1Cal.setText(datosConCalidad[3])
         bin.edtCon2Cal.setText(datosConCalidad[4])
         bin.edtCon3Cal.setText(datosConCalidad[5])
         bin.PromedioConductividadCalidad.setText(datosConCalidad[7])
     }

     fun chageActivity() {

         val intent = Intent(this, AguaCapturaActivity::class.java)
         Toast.makeText(applicationContext, "Datos guardados", Toast.LENGTH_SHORT).show()
         startActivity(intent)
     }

     fun llenarSpinner() {
         val termos : MutableList<String> = ArrayList()
         val phTrazable : MutableList<String> = ArrayList()
         val phCalidad : MutableList<String> = ArrayList()
         val db: SQLiteDatabase = con.readableDatabase

         val queryTerm = "SELECT * FROM TermometroCampo"
         val termometroModel = db.rawQuery(queryTerm, null)
         var cont: Int = 0
         if (termometroModel.moveToFirst()) {
             do {
                     if (cont == 0){
                         termos.add(termometroModel.getString(4))
                     }
                 termos.add(termometroModel.getString(0) + '/' + termometroModel.getString(4))
                 cont++
             } while (termometroModel.moveToNext())
         }
         val adTermo = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, termos
         )
         bin.spnTermo.adapter = adTermo
         bin.spnTermo2.adapter = adTermo

         val qePhTazable = "SELECT * FROM cat_phTrazable"
         val phTModel = db.rawQuery(qePhTazable, null)
         if (phTModel.moveToFirst()) {
             do {
                 phTrazable.add(phTModel.getString(1))
             } while (phTModel.moveToNext())
         }
         val adPhTrazable = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, phTrazable
         )

         val qePhCalidad = "SELECT * FROM cat_phCalidad"

         val phCModel = db.rawQuery(qePhCalidad, null)
         if (phCModel.moveToFirst()) {
             do {

                 phCalidad.add(phCModel.getString(1))
             } while (phCModel.moveToNext())

         }
         val adPhCalidad = ArrayAdapter(
             this,
             R.layout.simple_spinner_item, phCalidad
         )

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
         bin.spnConductividadCalidad.adapter = adpConductividad


     }

     fun valPhTrazable(spn: Spinner,lec1:EditText,lec2:EditText,lec3:EditText,Estado:EditText): Boolean {
         var sw:Boolean = false
         var num:String = ""
         var l1 :String = ""
         var l2 :String = ""
         var l3 :String = ""
         var ph :String = ""
         if (lec1.text.toString() != "" || lec2.text.toString() != ""|| lec3.text.toString() != ""){
             ph = spn.selectedItem.toString()
             num = lec1.text.toString()
             when(num.toFloat()){
                 in 0.0 .. 14.0 -> sw = true
                 else -> {
                     lec1.setError("No esta en el rango del 4 al 9")
                     sw = false
                 }
             }
             num = lec2.text.toString()
             when(num.toFloat()){
                 in 0.0 .. 14.0 -> sw = true
                 else -> {
                     lec2.setError("No esta en el rango del 4 al 9")
                     sw = false
                 }
             }
             num = lec3.text.toString()
             when(num.toFloat()){
                 in 0.0 .. 14.0 -> sw = true
                 else -> {
                     lec3.setError("No esta en el rango del 4 al 9")
                     sw = false
                 }
             }
             l1 = lec1.text.toString()
             l2 = lec2.text.toString()
             l3 = lec3.text.toString()


             if ((ph.toFloat() - l1.toFloat()) >= 0.5 || (ph.toFloat() - l1.toFloat()) <= 0.5 && (ph.toFloat() - l2.toFloat()) >= 0.5 || (ph.toFloat() - l2.toFloat()) <= 0.5 && (ph.toFloat() - l3.toFloat()) >= 0.5 || (ph.toFloat() - l3.toFloat()) <= 0.5){
                 //Lectura 1
                 if ((l1.toFloat() - l2.toFloat()) >= 0.03 || (l1.toFloat() - l2.toFloat()) <= -0.03){
                     sw = false
                     lec1.setError("Verifica los datos")
                     lec2.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
                 if ((l1.toFloat() - l3.toFloat()) >= 0.03 || (l1.toFloat() - l3.toFloat()) <= -0.03){
                     sw = false
                     lec1.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }

                 //Lectura2
                 if ((l2.toFloat() - l1.toFloat()) >= 0.03 || (l2.toFloat() - l1.toFloat()) <= -0.03){
                     sw = false
                     lec2.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
                 if ((l2.toFloat() - l3.toFloat()) >= 0.03 || (l2.toFloat() - l3.toFloat()) <= -0.03){
                     sw = false
                     lec2.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }

                 //Lectura3
                 if ((l3.toFloat() - l1.toFloat()) >= 0.03 || (l3.toFloat() - l1.toFloat()) <= -0.03){
                     sw = false
                     lec1.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
                 if ((l3.toFloat() - l2.toFloat()) >= 0.03 || (l3.toFloat() - l2.toFloat()) <= -0.03){
                     sw = false
                     lec2.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
             }else{
                 sw = false
                 Log.d("Val ph","El valor es mayor a 0.5")
             }

         }else{
             Estado.setError("Los campos están vacios") //error de campos vacios
         }

         if(sw == true){
             Estado.setText("ACEPTADO")
         }else{
             Estado.setText("RECHAZADO")
         }

         return sw
     }

     fun valPhCalidad(spn: Spinner,lec1:EditText,lec2:EditText,lec3:EditText,Promedio:EditText): Boolean {
         var sw:Boolean = false
         var num:String = ""
         var l1 :String = ""
         var l2 :String = ""
         var l3 :String = ""
         var ph :String = ""
         if (lec1.text.toString() != "" || lec2.text.toString() != ""|| lec3.text.toString() != ""){
             ph = spn.selectedItem.toString()
             num = lec1.text.toString()
             when(num.toFloat()){
                 in 0.0 .. 14.0 -> sw = true
                 else -> {
                     lec1.setError("No esta en el rango del 4 al 9")
                     sw = false
                 }
             }
             num = lec2.text.toString()
             when(num.toFloat()){
                 in 0.0 .. 14.0 -> sw = true
                 else -> {
                     lec2.setError("No esta en el rango del 4 al 9")
                     sw = false
                 }
             }
             num = lec3.text.toString()
             when(num.toFloat()){
                 in 0.0 .. 14.0 -> sw = true
                 else -> {
                     lec3.setError("No esta en el rango del 4 al 9")
                     sw = false
                 }
             }
             l1 = lec1.text.toString()
             l2 = lec2.text.toString()
             l3 = lec3.text.toString()

             var porPh = (ph.toFloat() * 2)/100

             if ((ph.toFloat() - l1.toFloat()) >= porPh || (ph.toFloat() - l1.toFloat()) <= porPh && (ph.toFloat() - l2.toFloat()) >= porPh || (ph.toFloat() - l2.toFloat()) <= porPh && (ph.toFloat() - l3.toFloat()) >= porPh || (ph.toFloat() - l3.toFloat()) <= porPh){
                 //Lectura 1
                 if ((l1.toFloat() - l2.toFloat()) >= 0.03 || (l1.toFloat() - l2.toFloat()) <= -0.03){
                     sw = false
                     lec1.setError("Verifica los datos")
                     lec2.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
                 if ((l1.toFloat() - l3.toFloat()) >= 0.03 || (l1.toFloat() - l3.toFloat()) <= -0.03){
                     sw = false
                     lec1.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }

                 //Lectura2
                 if ((l2.toFloat() - l1.toFloat()) >= 0.03 || (l2.toFloat() - l1.toFloat()) <= -0.03){
                     sw = false
                     lec2.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
                 if ((l2.toFloat() - l3.toFloat()) >= 0.03 || (l2.toFloat() - l3.toFloat()) <= -0.03){
                     sw = false
                     lec2.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }

                 //Lectura3
                 if ((l3.toFloat() - l1.toFloat()) >= 0.03 || (l3.toFloat() - l1.toFloat()) <= -0.03){
                     sw = false
                     lec1.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
                 if ((l3.toFloat() - l2.toFloat()) >= 0.03 || (l3.toFloat() - l2.toFloat()) <= -0.03){
                     sw = false
                     lec2.setError("Verifica los datos")
                     lec3.setError("Verifica los datos")
                 }else{
                     sw = true
                 }
             }else{
                 sw = false
                 Log.d("Val ph","El valor es mayor a 0.5")
             }

         }else{
             Promedio.setError("Los campos están vacios") //error de campos vacios
         }

         if(sw == true){
             val prom: Float
             prom = (l1.toFloat() + l2.toFloat() + l3.toFloat()) / 3
             Promedio.setText(prom.toString())
         }else{
             Promedio.setText("RECHAZADO")
         }

         return sw
     }


     fun valConTrazable(spn: Spinner,lec1:EditText,lec2:EditText,lec3:EditText,Estado:EditText): Boolean {
         var sw:Boolean = false
         var num:String = ""
         var l1 :String = ""
         var l2 :String = ""
         var l3 :String = ""
         var con :String = ""
         if (lec1.text.toString() != "" || lec2.text.toString() != ""|| lec3.text.toString() != ""){
             con = spn.selectedItem.toString()
             num = lec1.text.toString()

             l1 = lec1.text.toString()
             l2 = lec2.text.toString()
             l3 = lec3.text.toString()

             var porCon = ((con.toFloat() * 5) /100 )
             Log.d("Porcen ConTra",porCon.toString())



             if ((con.toFloat() - l1.toFloat()) >= porCon || (con.toFloat() - l1.toFloat()) <= porCon && (con.toFloat() - l2.toFloat()) >= porCon || (con.toFloat() - l2.toFloat()) <= porCon && (con.toFloat() - l3.toFloat()) >= porCon || (con.toFloat() - l3.toFloat()) <= porCon){
                 //Lectura 1
                sw = true
             }else{
                 sw = false
                 //Log.d("Val ph","El valor es mayor a 0.5")
                 Estado.setError("Por favor revisa tus lecturas") //error de campos vacios
             }

         }else{
             sw = false
             Estado.setError("Los campos están vacios") //error de campos vacios
         }

         if(sw == true){
             Estado.setText("ACEPTADO")
             Estado.clearFocus()
         }else{
             Estado.setText("RECHAZADO")
         }

         return sw
     }

     fun valConCalidad(spn: Spinner,lec1:EditText,lec2:EditText,lec3:EditText,Estado:EditText): Boolean {
         var sw:Boolean = false
         var num:String = ""
         var l1 :String = ""
         var l2 :String = ""
         var l3 :String = ""
         var con :String = ""
         if (lec1.text.toString() != "" || lec2.text.toString() != ""|| lec3.text.toString() != ""){
             con = spn.selectedItem.toString()
             num = lec1.text.toString()

             l1 = lec1.text.toString()
             l2 = lec2.text.toString()
             l3 = lec3.text.toString()

             var porCon = ((con.toFloat() * 5) /100 )


             if ((con.toFloat() - l1.toFloat()) >= porCon || (con.toFloat() - l1.toFloat()) <= porCon && (con.toFloat() - l2.toFloat()) >= porCon || (con.toFloat() - l2.toFloat()) <= porCon && (con.toFloat() - l3.toFloat()) >= porCon || (con.toFloat() - l3.toFloat()) <= porCon){
                 //Lectura 1
                 sw= true
             }else{
                 sw = false
                 //Log.d("Val ph","El valor es mayor a 0.5")
                 Estado.setError("Por favor revisa tus lecturas") //error de campos vacios
             }

         }else{
             Estado.setError("Los campos están vacios") //error de campos vacios
         }

         if(sw == true){
             val prom = (l1.toFloat() + l2.toFloat()+ l3.toFloat()) / 3
             Estado.setText(prom.toString())
         }else{
             Estado.setText("RECHAZADO")
         }

         return sw
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

     //FUNCNONES DE LOCALIZACIÓN
     private fun allPermissionGrantedGPS() = Companion.REQUIRED_PERMISSIONS_GPS.all{
         ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
     }

     private fun ubicacionActual(){
         if (checkPermission()){
             if (isLocationEnabled()){
                 if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                     mFusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->
                         var location: Location? = task.result
                         if(location == null){
                             requestNewLocationData()
                         } else {
                             bin.edtLatitud.setText(location.latitude.toString())
                             bin.edtLongitud.setText(location.longitude.toString())

                         }

                     }
                 }

             }else{
                 Toast.makeText(this, "Activa la ubicación", Toast.LENGTH_SHORT).show()
                 val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
             }
         } else {
             ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)

         }
     }

     private fun requestNewLocationData(){
         var mLocationRequest = LocationRequest()
         mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
         mLocationRequest.interval = 0
         mLocationRequest.fastestInterval = 0
         mLocationRequest.numUpdates = 1
         mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
         if (ActivityCompat.checkSelfPermission(
                 this,
                 Manifest.permission.ACCESS_FINE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 this,
                 Manifest.permission.ACCESS_COARSE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             // TODO: Consider calling
             //    ActivityCompat#requestPermissions
             // here to request the missing permissions, and then overriding
             //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
             //                                          int[] grantResults)
             // to handle the case where the user grants the permission. See the documentation
             // for ActivityCompat#requestPermissions for more details.
             return
         }
         mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper())
     }
     private val mLocationCallBack = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult){
             var mLastLocation : Location = locationResult.lastLocation
             bin.edtLatitud.setText(mLastLocation.latitude.toString())
             bin.edtLongitud.setText(mLastLocation.longitude.toString())

         }
     }
     private fun isLocationEnabled(): Boolean {
         var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
         return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
             LocationManager.NETWORK_PROVIDER
         )
     }


     private fun checkPermission(): Boolean {
         if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
             ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
         ){
             return true
         }
         return false
     }

     companion object{
         private val REQUIRED_PERMISSIONS_GPS = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
     }

 }
