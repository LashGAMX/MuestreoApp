

package com.acama.muestreoapp

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.acama.muestreoapp.databinding.ActivityMuestraSimpleBinding
import com.acama.muestreoapp.models.*
import kotlinx.android.synthetic.main.activity_muestra_simple.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.round
import kotlin.math.roundToInt


class MuestraSimpleActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityMuestraSimpleBinding
    private lateinit var folio:String
    private var idSol:Int = 0
    private lateinit var numToma:String
    private lateinit var con: DataBaseHandler

    private var estado:Int = 0
    private var state = 0
    private var toma = 0

    private var sw1 = false
    private var sw2 = false
    private var sw3 = false
    private var sw4 = false

    private var ph_muestra: MutableList<String> = mutableListOf()
    private var temperaturaMuestra: MutableList<String> = mutableListOf()
    private var temperaturaAmbiente: MutableList<String> = mutableListOf()
    private var conductividadMuestra: MutableList<String> = mutableListOf()
    private var gastoMuestra: MutableList<String> = mutableListOf()
    private var phCaliadMuestra: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        bin = ActivityMuestraSimpleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bin.root)
        con = DataBaseHandler(this)
        val db: SQLiteDatabase = con.readableDatabase

        folio = intent.getStringExtra("folio").toString()
        numToma = intent.getStringExtra("numToma").toString()
        bin.txtNumMuestra.text = numToma





        val qrSolGenModel = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
        val solGenModel = db.rawQuery(qrSolGenModel, null)
        if (solGenModel.moveToFirst()) {
            do {
                idSol = solGenModel.getInt(0)
            } while (solGenModel.moveToNext())
        }
        LlenarSpinners()
        EncontrarDatos()
        MostrarDatos()
        ValCancelada()

        bin.edtPh1.addTextChangedListener {
            try {
                validarPH()
            } catch (e: Exception){

            }
        }
        bin.edtPh2.addTextChangedListener {
            try {
                validarPH()
            } catch (e: Exception){

            }
        }
        bin.edtPh3.addTextChangedListener {
            try {
                validarPH()
            } catch (e: Exception){

            }
        }
        bin.edtTemp1.addTextChangedListener {
            try {
                valProm()
            } catch (e: Exception){

            }
        }
        bin.edtTemp2.addTextChangedListener {
            try {
                valProm()
            } catch (e: Exception){

            }
        }
        bin.edtTemp3.addTextChangedListener {
            try {
                valProm()
            } catch (e: Exception){

            }
        }
        bin.edtControlCal1.addTextChangedListener {
            try {
                valControlCalidad()
            } catch (e: Exception){

            }
        }
        bin.edtControlCal2.addTextChangedListener {
            try {
                valControlCalidad()
            } catch (e: Exception){

            }
        }
        bin.edtControlCal3.addTextChangedListener {
            try {
                valControlCalidad()
            } catch (e: Exception){

            }
        }
        bin.edtCon1.addTextChangedListener {
            try {
                valCon()
            } catch (e: Exception){

            }
        }
        bin.edtCon2.addTextChangedListener {
            try {
                valCon()
            } catch (e: Exception){

            }
        }
        bin.edtCon3.addTextChangedListener {
            try {
                valCon()
            } catch (e: Exception){

            }
        }
        bin.edtGasto1.addTextChangedListener {
            try {
                valGasto()
            } catch (e: Exception){

            }
        }
        bin.edtGasto2.addTextChangedListener {
            try {
                valGasto()
            } catch (e: Exception){

            }
        }
        bin.edtGasto3.addTextChangedListener {
            try {
                valGasto()
            } catch (e: Exception){

            }
        }

        bin.btnCancelar.setOnClickListener{
            DialogCancelar()

        }
        bin.btnRegresar.setOnClickListener{
            DialogVolver()
        }





        //Boton guardar datos
        bin.btnGuardar.setOnClickListener{
                guardarDatos()
        }


        getDate()

    }
    fun validarPH(){
        val ph1 = bin.edtPh1.text.toString()
        val ph2 = bin.edtPh2.text.toString()
        val ph3 = bin.edtPh3.text.toString()
        var sw = false
        if ((ph1.toFloat() - ph2.toFloat() >= 0.05 || ph1.toFloat() - ph2.toFloat() <= 0.05) && (ph1.toFloat() - ph3.toFloat() >= 0.05 || ph1.toFloat() - ph3.toFloat() <= 0.05)){
            sw = true
        }else{
            sw = false
        }
        if ((ph2.toFloat() - ph1.toFloat() >= 0.05 || ph2.toFloat() - ph1.toFloat() <= 0.05) && (ph2.toFloat() - ph3.toFloat() >= 0.05 || ph2.toFloat() - ph3.toFloat() <= 0.05)){
            sw = true
        }else{
            sw = false
        }
        if ((ph3.toFloat() - ph1.toFloat() >= 0.05 || ph3.toFloat() - ph1.toFloat() <= 0.05) && (ph3.toFloat() - ph1.toFloat() >= 0.05 || ph3.toFloat() - ph1.toFloat() <= 0.05)){
            sw = true
        }else{
            sw = false
        }

        if (sw == true){
            sw1 = true
            val promedio =  (ph1.toFloat() + ph2.toFloat() + ph3.toFloat()) / 3
            bin.txtPromPh.text = ((promedio * 1000.0).roundToInt() / 1000.0).toString()
        }else{
            sw1 = false
            bin.edtPh1.setError("Comprueba los datos")
            bin.edtPh2.setError("Comprueba los datos")
            bin.edtPh3.setError("Comprueba los datos")
        }
    }
    fun valProm(){
        val temp1 = bin.edtTemp1.text.toString()
        val temp2 = bin.edtTemp2.text.toString()
        val temp3 = bin.edtTemp3.text.toString()
        var sw = false
        if ((temp1.toFloat() - temp2.toFloat() >= 1 || temp1.toFloat() - temp2.toFloat() <= 1) && (temp1.toFloat() - temp3.toFloat() >= 1 || temp1.toFloat() - temp3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((temp2.toFloat() - temp1.toFloat() >= 1 || temp2.toFloat() - temp1.toFloat() <= 1) && (temp2.toFloat() - temp3.toFloat() >= 1 || temp2.toFloat() - temp3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((temp3.toFloat() - temp1.toFloat() >= 1 || temp3.toFloat() - temp1.toFloat() <= 1) && (temp3.toFloat() - temp1.toFloat() >= 1 || temp3.toFloat() - temp1.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }

        if (sw == true){
            sw2 = true
            val promedio = (temp1.toFloat() + temp2.toFloat() + temp3.toFloat()) / 3
            bin.txtTempProm.text = ((promedio * 1000.0).roundToInt() / 1000.0).toString()
        }else{
            sw2 = false
            bin.edtTemp1.setError("Comprueba los datos")
            bin.edtTemp2.setError("Comprueba los datos")
            bin.edtTemp3.setError("Comprueba los datos")
        }

    }
    fun valCalidad(){
        val temp1 = bin.edtControlCal1.text.toString()
        val temp2 = bin.edtControlCal2.text.toString()
        val temp3 = bin.edtControlCal3.text.toString()
        var sw = false
        if ((temp1.toFloat() - temp2.toFloat() >= 1 || temp1.toFloat() - temp2.toFloat() <= 1) && (temp1.toFloat() - temp3.toFloat() >= 1 || temp1.toFloat() - temp3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((temp2.toFloat() - temp1.toFloat() >= 1 || temp2.toFloat() - temp1.toFloat() <= 1) && (temp2.toFloat() - temp3.toFloat() >= 1 || temp2.toFloat() - temp3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((temp3.toFloat() - temp1.toFloat() >= 1 || temp3.toFloat() - temp1.toFloat() <= 1) && (temp3.toFloat() - temp1.toFloat() >= 1 || temp3.toFloat() - temp1.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }

        if (sw == true){
            sw2 = true
            val promedio = (temp1.toFloat() + temp2.toFloat() + temp3.toFloat()) / 3
            bin.txtTempPromA.text = ((promedio * 1000.0).roundToInt() / 1000.0).toString()
        }else{
            sw2 = false
            bin.edtControlCal1.setError("Comprueba los datos")
            bin.edtControlCal2.setError("Comprueba los datos")
            bin.edtControlCal3.setError("Comprueba los datos")
        }

    }
    fun valControlCalidad(){
        val amb1 = bin.edtControlCal1.text.toString()
        val amb2 = bin.edtControlCal2.text.toString()
        val amb3 = bin.edtControlCal3.text.toString()
        var sw = false
        if ((amb1.toFloat() - amb2.toFloat() >= 1 || amb1.toFloat() - amb2.toFloat() <= 1) && (amb1.toFloat() - amb3.toFloat() >= 1 || amb1.toFloat() - amb3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((amb2.toFloat() - amb1.toFloat() >= 1 || amb2.toFloat() - amb1.toFloat() <= 1) && (amb2.toFloat() - amb3.toFloat() >= 1 || amb2.toFloat() - amb3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((amb3.toFloat() - amb1.toFloat() >= 1 || amb3.toFloat() - amb1.toFloat() <= 1) && (amb3.toFloat() - amb1.toFloat() >= 1 || amb3.toFloat() - amb1.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }

        if (sw == true){
            sw2 = true
            val promedio = (amb1.toFloat() + amb2.toFloat() + amb3.toFloat()) / 3
            bin.txtControlCalProm.text = ((promedio * 100.0).roundToInt() / 100.0).toString()
        }else{
            sw2 = false
            bin.edtCon1.setError("Comprueba los datos")
            bin.edtCon1.setError("Comprueba los datos")
            bin.edtCon1.setError("Comprueba los datos")
        }
    }
    fun valCon(){
        val con1 = bin.edtCon1.text.toString()
        val con2 = bin.edtCon2.text.toString()
        val con3 = bin.edtCon3.text.toString()
        var sw = false
        if ((con1.toFloat() - con2.toFloat() >= 5 || con1.toFloat() - con2.toFloat() <= 5) && (con1.toFloat() - con3.toFloat() >= 5 || con1.toFloat() - con3.toFloat() <= 5)){
            sw = true
        }else{
            sw = false
        }
        if ((con2.toFloat() - con1.toFloat() >= 5 || con2.toFloat() - con1.toFloat() <= 5) && (con2.toFloat() - con3.toFloat() >= 5 || con2.toFloat() - con3.toFloat() <= 5)){
            sw = true
        }else{
            sw = false
        }
        if ((con3.toFloat() - con1.toFloat() >= 5 || con3.toFloat() - con1.toFloat() <= 5) && (con3.toFloat() - con2.toFloat() >= 5 || con3.toFloat() - con2.toFloat() <= 5)){
            sw = true
        }else{
            sw = false
        }

        if (sw == true){
            sw3 = true
            val promedio = (con1.toFloat() + con2.toFloat() + con3.toFloat()) / 3
            bin.txtConProm.text = ((promedio * 1000.0).roundToInt() / 1000.0).toString()
        }else{
            sw3 = false
            bin.edtCon1.setError("Comprueba los datos")
            bin.edtCon2.setError("Comprueba los datos")
            bin.edtCon3.setError("Comprueba los datos")
        }
    }
    fun valGasto(){
        val gasto1 = bin.edtGasto1.text.toString()
        val gasto2 = bin.edtGasto2.text.toString()
        val gasto3 = bin.edtGasto3.text.toString()
        var sw = false
        if ((gasto1.toFloat() - gasto2.toFloat() >= 1 || gasto1.toFloat() - gasto2.toFloat() <= 1) && (gasto1.toFloat() - gasto3.toFloat() >= 1 || gasto1.toFloat() - gasto3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((gasto2.toFloat() - gasto1.toFloat() >= 1 || gasto2.toFloat() - gasto1.toFloat() <= 1) && (gasto2.toFloat() - gasto3.toFloat() >= 1 || gasto2.toFloat() - gasto3.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }
        if ((gasto3.toFloat() - gasto1.toFloat() >= 1 || gasto3.toFloat() - gasto1.toFloat() <= 1) && (gasto3.toFloat() - gasto2.toFloat() >= 1 || gasto3.toFloat() - gasto2.toFloat() <= 1)){
            sw = true
        }else{
            sw = false
        }

        if (sw == true){
            sw4 = true
            val promedio = (gasto1.toFloat() + gasto2.toFloat() + gasto3.toFloat()) / 3
            var gastoPromedio = ((promedio * 1000.0).roundToInt() / 1000.0).toString()

            bin.txtGastoProm.text = gastoPromedio
        }else{
            sw4 = false
            bin.edtGasto1.setError("Comprueba los datos")
            bin.edtGasto2.setError("Comprueba los datos")
            bin.edtGasto3.setError("Comprueba los datos")
        }
    }
    fun getDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        bin.btnFecha.setOnClickListener {
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val m = mMonth + 1;
                bin.txtFecha.text = ""+mYear+"-"+m+"-"+mDay
            },year,month,day)
            dpd.show()
        }
    }
  /*  fun getHora(){
        val c = Calendar.getInstance()
        val mHour = c.get(Calendar.HOUR_OF_DAY)
        val mMinute = c.get(Calendar.MINUTE)
        bin.btnHora.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this,
                { view, hourOfDay, minute -> bin.txtHora.setText("$hourOfDay:$minute") },
                mHour,
                mMinute,
                false
            )
            timePickerDialog.show()
        }

    } */
    fun cancelar(){
        val dbw: SQLiteDatabase = con.writableDatabase
        val db: SQLiteDatabase = con.readableDatabase
        val queryCanceladas = "SELECT * FROM canceladas WHERE Folio = '$folio' AND  Muestra = '$numToma'"
        val canceladas = db.rawQuery(queryCanceladas, null)
      if (canceladas.moveToFirst()){

      } else {
          var modelCan = Canceladas(
              folio,
              idSol,
              numToma.toInt(),
              1,

              )
          con.insertCanceladas(modelCan)

          val h = bin.edtHora.text.toString()
          val m = bin.txtMin.text.toString()
          val hora = h + ":" + m
          val cvModel = ContentValues()
          val toma = numToma.toInt()

          cvModel.put("Fecha",bin.txtFecha.text.toString())
          cvModel.put("Hora",hora)

          dbw.update("ph_muestra", cvModel, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)
          // Guardar TempAmbiente)
          val cv5Model = ContentValues()
          cv5Model.put("Num_toma",numToma.toInt())
          cv5Model.put("TempA1",bin.edtTempA1.text.toString())

          dbw.update("temperatura_ambiente", cv5Model, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)


      }
      estado = 0
      toma = 0
      state = 0
    }


    fun guardarDatos(){
        val dbw: SQLiteDatabase = con.writableDatabase
        val h = bin.edtHora.text.toString()
        val m = bin.txtMin.text.toString()
        val hora = h + ":" + m
        val toma = numToma.toInt()

        val cvModel = ContentValues()
            cvModel.put("Num_toma",numToma.toInt())
            cvModel.put("Materia",bin.spnMateriaFlotante.selectedItem.toString())
            cvModel.put("Olor",bin.spnOlor.selectedItem.toString())
            cvModel.put("Color",bin.spnColor.selectedItem.toString())
            cvModel.put("Ph1",bin.edtPh1.text.toString())
            cvModel.put("Ph2",bin.edtPh2.text.toString())
            cvModel.put("Ph3",bin.edtPh3.text.toString())
            cvModel.put("Promedio",bin.txtPromPh.text.toString())
            cvModel.put("Fecha",bin.txtFecha.text.toString())
            cvModel.put("Hora",hora)

        dbw.update("ph_muestra", cvModel, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)
        //con.insertPhMuestra(cvModel)
    // Guardar TempMuestra (Temperatura del agua)
        val cv2Model = ContentValues()
            cv2Model.put("Num_toma",numToma.toInt())
            cv2Model.put("Temp1",bin.edtTemp1.text.toString())
            cv2Model.put("Temp2",bin.edtTemp2.text.toString())
            cv2Model.put("Temp3",bin.edtTemp3.text.toString())
            cv2Model.put("Promedio",bin.txtTempProm.text.toString())

        dbw.update("temperatura_muestra", cv2Model, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)
        // Guardar TempAmbiente)
        val cv5Model = ContentValues()
            cv5Model.put("Num_toma",numToma.toInt())
            cv5Model.put("TempA1",bin.edtTempA1.text.toString())
            cv5Model.put("TempA2",bin.edtTempA2.text.toString())
            cv5Model.put("TempA3",bin.edtTempA3.text.toString())
            cv5Model.put("PromedioA",bin.txtTempPromA.text.toString())

            dbw.update("temperatura_ambiente", cv5Model, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)
        //Guardar Conductividad
        val cv3Model = ContentValues()
            cv3Model.put("Num_toma",numToma.toInt())
            cv3Model.put("Conductividad1",bin.edtCon1.text.toString())
            cv3Model.put("Conductividad2",bin.edtCon2.text.toString())
            cv3Model.put("Conductividad3",bin.edtCon3.text.toString())
            cv3Model.put("Promedio",bin.txtConProm.text.toString())
        dbw.update("conductividad_muestra", cv3Model, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)

        //Guardar Gasto
        val cv4Model = ContentValues()
            cv4Model.put("Num_toma",numToma.toInt())
            cv4Model.put("Gasto1",bin.edtGasto1.text.toString())
            cv4Model.put("Gasto2",bin.edtGasto2.text.toString())
            cv4Model.put("Gasto3",bin.edtGasto3.text.toString())
            cv4Model.put("Promedio",bin.txtGastoProm.text.toString())

        dbw.update("gasto_muestra", cv4Model, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)
        // Guardar ph calidad muestra

        val cv6Model = ContentValues()
            cv6Model.put("Num_toma", numToma.toInt())
        cv6Model.put("Lectura1C", bin.edtControlCal1.text.toString())
        cv6Model.put("Lectura2C", bin.edtControlCal2.text.toString())
        cv6Model.put("Lectura3C", bin.edtControlCal3.text.toString())
       // cv6Model.put("EstadoC", "ACEPTADO")
        cv6Model.put("PromedioC", bin.txtControlCalProm.text.toString())

        dbw.update("ph_calidadMuestra", cv6Model, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)


        onBackPressed()
    }
    fun EncontrarDatos(){
        //Consultar datos guardados de
        val db: SQLiteDatabase = con.readableDatabase
        val qrphMuestra = "SELECT * FROM ph_muestra WHERE Id_solicitud = '$idSol' AND Num_toma = '$numToma'"
        val phMuestrModel = db.rawQuery(qrphMuestra, null)
        if(phMuestrModel.moveToFirst()){
            do{
                ph_muestra.add(phMuestrModel.getString(1))
                ph_muestra.add(phMuestrModel.getString(2))
                ph_muestra.add(phMuestrModel.getString(3))
                ph_muestra.add(phMuestrModel.getString(4))
                ph_muestra.add(phMuestrModel.getString(5))
                ph_muestra.add(phMuestrModel.getString(6))
                ph_muestra.add(phMuestrModel.getString(7))
                ph_muestra.add(phMuestrModel.getString(8))
                ph_muestra.add(phMuestrModel.getString(9))
                ph_muestra.add(phMuestrModel.getString(10))
                ph_muestra.add(phMuestrModel.getString(11))

            } while (phMuestrModel.moveToNext())
        }
        val qrTempMuestra = "SELECT * FROM temperatura_muestra WHERE Id_solicitud = '$idSol'  AND Num_toma = '$numToma'"
        val tempMuestraModel = db.rawQuery(qrTempMuestra, null)
        if(tempMuestraModel.moveToFirst()){
            do{
                temperaturaMuestra.add(tempMuestraModel.getString(1))
                temperaturaMuestra.add(tempMuestraModel.getString(2))
                temperaturaMuestra.add(tempMuestraModel.getString(3))
                temperaturaMuestra.add(tempMuestraModel.getString(4))
                temperaturaMuestra.add(tempMuestraModel.getString(5))
                temperaturaMuestra.add(tempMuestraModel.getString(6))

            } while (tempMuestraModel.moveToNext())
        }
        val qrTempAmbiente = "SELECT * FROM temperatura_ambiente WHERE Id_solicitud = '$idSol'  AND Num_toma = '$numToma'"
        val tempAmbienteModel = db.rawQuery(qrTempAmbiente, null)
        if(tempAmbienteModel.moveToFirst()){
            do{
                temperaturaAmbiente.add(tempAmbienteModel.getString(3))


            } while (tempAmbienteModel.moveToNext())
        }
        val qrConductividad = "SELECT * FROM conductividad_muestra WHERE Id_solicitud = '$idSol'  AND Num_toma = '$numToma'"
        val conductividadModel = db.rawQuery(qrConductividad, null)
        if(conductividadModel.moveToFirst()){
            do{
                conductividadMuestra.add(conductividadModel.getString(1))
                conductividadMuestra.add(conductividadModel.getString(2))
                conductividadMuestra.add(conductividadModel.getString(3))
                conductividadMuestra.add(conductividadModel.getString(4))
                conductividadMuestra.add(conductividadModel.getString(5))
                conductividadMuestra.add(conductividadModel.getString(6))

            } while (conductividadModel.moveToNext())
        }
        val qrGasto = "SELECT * FROM gasto_muestra WHERE Id_solicitud = '$idSol'  AND Num_toma = '$numToma'"
        val gastoModel = db.rawQuery(qrGasto, null)
        if(gastoModel.moveToFirst()){
            do{
                gastoMuestra.add(gastoModel.getString(1))
                gastoMuestra.add(gastoModel.getString(2))
                gastoMuestra.add(gastoModel.getString(3))
                gastoMuestra.add(gastoModel.getString(4))
                gastoMuestra.add(gastoModel.getString(5))
                gastoMuestra.add(gastoModel.getString(5))

            } while (gastoModel.moveToNext())
        }
        val qrPhCalidadMuestra = "SELECT * FROM ph_CalidadMuestra WHERE Id_solicitud = '$idSol'  AND Num_toma = '$numToma'"
        val phCalidadMuestraModel = db.rawQuery(qrPhCalidadMuestra, null)
        if(phCalidadMuestraModel.moveToFirst()){
            do{
                phCaliadMuestra.add(phCalidadMuestraModel.getString(1))
                phCaliadMuestra.add(phCalidadMuestraModel.getString(2))
                phCaliadMuestra.add(phCalidadMuestraModel.getString(3))
                phCaliadMuestra.add(phCalidadMuestraModel.getString(4))
                phCaliadMuestra.add(phCalidadMuestraModel.getString(5))
                phCaliadMuestra.add(phCalidadMuestraModel.getString(6))

            } while (phCalidadMuestraModel.moveToNext())
        }
    }
    fun MostrarDatos(){
//PH
        bin.edtPh1.setText(ph_muestra[5])
        bin.edtPh2.setText(ph_muestra[6])
        bin.edtPh3.setText(ph_muestra[7])
        bin.txtPromPh.text = ph_muestra[8]
        bin.txtFecha.text = ph_muestra[9]
        var hora = ph_muestra[10]
        if (hora != ""){
            val delim = ":"
            val lista = hora.split(delim)
            bin.edtHora.setText( lista[0])
            bin.txtMin.setText(lista[1])
        }

    // Selectores ph
        val arrMateriaFloante = listOf<String>("Selecciona uno","Ausente","Presente")
        val arrColor : MutableList<String> = ArrayList()
        val arrOlor = listOf<String>("Selecciona uno","Si","No")
        val db: SQLiteDatabase = con.readableDatabase

        val queryColor = "SELECT * FROM Color"
        val colorModel = db.rawQuery(queryColor, null)
        if (colorModel.moveToFirst()){
            arrColor.add("Selecciona uno")
            do {
                arrColor.add(colorModel.getString(1))
            } while (colorModel.moveToNext())
        }
        val adColor = ArrayAdapter(
            this,
            R.layout.simple_spinner_item, arrColor
        )

        val adaptador1 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrMateriaFloante)
        val adaptador3 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrOlor)

        bin.spnMateriaFlotante.adapter = adaptador1
        bin.spnColor.adapter = adColor
        bin.spnOlor.adapter = adaptador3

        val materia : String = ph_muestra[2]
        val olor : String = ph_muestra[3]
        val color : String = ph_muestra[4]
        val materiaPosition : Int = adaptador1.getPosition(materia)
        val colorPosition : Int = adColor.getPosition(color)
        val olorPosition : Int = adaptador3.getPosition(olor)
        bin.spnMateriaFlotante.setSelection(materiaPosition)
        bin.spnColor.setSelection(colorPosition)
        bin.spnOlor.setSelection(olorPosition)

//TEMPERATURA AGUA
        bin.edtTemp1.setText(temperaturaMuestra[2])
        bin.edtTemp2.setText(temperaturaMuestra[3])
        bin.edtTemp3.setText(temperaturaMuestra[4])
        bin.txtTempProm.text = temperaturaMuestra[5]
//TEMPERATURA AMBIENTE
        bin.edtTempA1.setText(temperaturaAmbiente[0])
//        bin.txtTempPromA.text = temperaturaAmbiente[6]
//CONDUCTIVIDAD MUESTRA
        bin.edtCon1.setText(conductividadMuestra[2])
        bin.edtCon2.setText(conductividadMuestra[3])
        bin.edtCon3.setText(conductividadMuestra[4])
        bin.txtConProm.text = conductividadMuestra[4]
//GASTO
        bin.edtGasto1.setText(gastoMuestra[3])
        bin.edtGasto2.setText(gastoMuestra[4])
        bin.edtGasto3.setText(gastoMuestra[5])
        bin.txtGastoProm.text = gastoMuestra[5]
//CONTROL CALIDAD
        bin.edtControlCal1.setText(phCaliadMuestra[2])
        bin.edtControlCal2.setText(phCaliadMuestra[3])
        bin.edtControlCal3.setText(phCaliadMuestra[4])
        bin.txtControlCalProm.text = phCaliadMuestra[4]
    }
    fun LlenarSpinners(){

        val arrMateriaFloante = listOf<String>("Selecciona uno","Ausente","Presente")
        val arrColor : MutableList<String> = ArrayList()
        val arrOlor = listOf<String>("Selecciona uno","Si","No")
        val db: SQLiteDatabase = con.readableDatabase

        val queryColor = "SELECT * FROM Color"
        val colorModel = db.rawQuery(queryColor, null)
        if (colorModel.moveToFirst()){
                arrColor.add("Selecciona uno")
            do {
                    arrColor.add(colorModel.getString(1))
            } while (colorModel.moveToNext())
        }
        val adColor = ArrayAdapter(
            this,
            R.layout.simple_spinner_item, arrColor
        )

        val adaptador1 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrMateriaFloante)
        val adaptador3 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrOlor)

        bin.spnMateriaFlotante.adapter = adaptador1
        bin.spnColor.adapter = adColor
        bin.spnOlor.adapter = adaptador3
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
    fun ValCancelada() {

        val db: SQLiteDatabase = con.readableDatabase
        val query = "SELECT * FROM canceladas WHERE Muestra = '$numToma' AND Id_solicitud = '$idSol'"
        val model = db.rawQuery(query, null)
        if (model.getCount() > 0) {
            model.moveToFirst()
            state = model.getInt(model.getColumnIndex("Estado"))
            toma = model.getInt(model.getColumnIndex("Muestra"))
        }
        model.close()

            if (toma == numToma.toInt()) {

                    DialogDesactivado()
            } else {
                EncontrarDatos()
                MostrarDatos()
            }

    }
    fun DialogCancelar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cuidado")
        builder.setMessage("¿Seguro de que quieres cancelar esta muestra?")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        android.R.string.yes, Toast.LENGTH_SHORT
                    ).show()
                    //GUARDAR FECHA Y HORA AL MOMENTO DE CANCELAR
                    cancelGurdado()
                    cancelar() //Agrega datos a tabal cancelados
                    onBackPressed()
                    //Toast.makeText(this, "muestra cancelada", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(
                        applicationContext,
                        android.R.string.no, Toast.LENGTH_SHORT
                    ).show()
                }
                builder.show()


    }
    fun DialogError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Faltan datos")
        builder.setMessage("Necesitas capturar FECHA, HORA y TEMPERATURA AMBIENTE")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(
                applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT
            ).show()
            //GUARDAR FECHA Y HORA AL MOMENTO DE CANCELAR

            //Toast.makeText(this, "muestra cancelada", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(
                applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()


    }
    fun cancelGurdado(){
        val dbw: SQLiteDatabase = con.writableDatabase
        val h = bin.edtHora.text.toString()
        val m = bin.txtMin.text.toString()
        val hora = h + ":" + m
        val toma = numToma.toInt()

        val cvModel = ContentValues()
        cvModel.put("Num_toma",numToma.toInt())
        cvModel.put("Materia",bin.spnMateriaFlotante.selectedItem.toString())
        cvModel.put("Olor",bin.spnOlor.selectedItem.toString())
        cvModel.put("Color",bin.spnColor.selectedItem.toString())
        cvModel.put("Ph1",bin.edtPh1.text.toString())
        cvModel.put("Ph2",bin.edtPh2.text.toString())
        cvModel.put("Ph3",bin.edtPh3.text.toString())
        cvModel.put("Promedio",bin.txtPromPh.text.toString())
        cvModel.put("Fecha",bin.txtFecha.text.toString())
        cvModel.put("Hora",hora)

        dbw.update("ph_muestra", cvModel, "Id_solicitud = "+idSol+ " AND Num_toma = "+toma, null)
        //con.insertPhMuestra(cvModel)
        Toast.makeText(this, "muestra modificada", Toast.LENGTH_SHORT).show()
    }
    fun DialogDesactivado(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Muestra cancelada")
        builder.setMessage("Esta muestra ha sido cancelada!")
        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.ok, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }
        builder.show()

    }
}

