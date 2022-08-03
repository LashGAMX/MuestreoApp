package com.acama.muestreoapp

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityMuestraSimpleBinding
import com.acama.muestreoapp.models.*
import java.util.*

class MuestraSimpleActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityMuestraSimpleBinding
    private lateinit var folio:String
    private var idSol:Int = 0
    private lateinit var numToma:String
    private lateinit var con: DataBaseHandler

    private var estado:Int = 0

    private var sw1 = false
    private var sw2 = false
    private var sw3 = false
    private var sw4 = false

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

        bin.btnCancelar.setOnClickListener{
            cancelar()
        }
        bin.btnRegresar.setOnClickListener{
            DialogVolver()
        }

        bin.btnValPH.setOnClickListener {
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
            bin.txtPromPh.text = ((ph1.toFloat() + ph2.toFloat() + ph3.toFloat()) / 3).toString()
        }else{
            sw1 = false
            bin.edtPh1.setError("Comprueba los datos")
            bin.edtPh2.setError("Comprueba los datos")
            bin.edtPh3.setError("Comprueba los datos")
        }

    }

        bin.btnValProm.setOnClickListener {
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
                bin.txtTempProm.text = ((temp1.toFloat() + temp2.toFloat() + temp3.toFloat()) / 3).toString()
            }else{
                sw2 = false
                bin.edtTemp1.setError("Comprueba los datos")
                bin.edtTemp2.setError("Comprueba los datos")
                bin.edtTemp3.setError("Comprueba los datos")
            }

        }

        bin.btnValCon.setOnClickListener {
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
                bin.txtConProm.text = ((con1.toFloat() + con2.toFloat() + con3.toFloat()) / 3).toString()
            }else{
                sw3 = false
                bin.edtCon1.setError("Comprueba los datos")
                bin.edtCon2.setError("Comprueba los datos")
                bin.edtCon3.setError("Comprueba los datos")
            }
        }

        bin.btnValGasto.setOnClickListener {
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
                bin.txtGastoProm.text = ((gasto1.toFloat() + gasto2.toFloat() + gasto3.toFloat()) / 3).toString()
            }else{
                sw4 = false
                bin.edtGasto1.setError("Comprueba los datos")
                bin.edtGasto2.setError("Comprueba los datos")
                bin.edtGasto3.setError("Comprueba los datos")
            }
        }


        //Boton guardar datos
        bin.btnGuardar.setOnClickListener{
            //guardarDatos()
            if (sw1 == true && sw2 == true && sw3 == true && sw4 == true){
                Toast.makeText(this,"Datos guardados correctamente",Toast.LENGTH_SHORT).show()
                guardarDatos()
            }else{
                Toast.makeText(this,"Error al guardar los datos",Toast.LENGTH_SHORT).show()
            }
        }

       LlenarSpinners()
        getDate()

    }
    fun getDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        bin.btnFecha.setOnClickListener {
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
             bin.txtFecha.text = ""+mDay+"/"+mMonth+"/"+mYear
            },year,month,day)
            dpd.show()
        }
    }
    fun cancelar(){
        val dbw: SQLiteDatabase = con.writableDatabase

        var modelCan = Canceladas(
            estado.toString(),
            numToma.toInt(),
            idSol

        )
        con.insertCanceladas(modelCan)
    }

    fun guardarDatos(){
        val dbw: SQLiteDatabase = con.writableDatabase

        var cvModel = PhMuestra(
            idSol,
            numToma.toInt(),
            bin.spnMateriaFlotante.selectedItem.toString(),
            bin.spnOlor.selectedItem.toString(),
            bin.spnColor.selectedItem.toString(),
            bin.edtPh1.text.toString(),
            bin.edtPh2.text.toString(),
            bin.edtPh3.text.toString(),
            bin.txtPromPh.text.toString(),
            bin.txtFecha.text.toString()
        )
        con.insertPhMuestra(cvModel)
    // Guardar TempMuestra
        val cv2Model = TempMuestra(
            idSol,
            numToma.toInt(),
            bin.edtTemp1.text.toString(),
            bin.edtTemp2.text.toString(),
            bin.edtTemp3.text.toString(),
            bin.txtTempProm.text.toString(),
        )
        con.insertTempMuestra(cv2Model)
        //Guardar Conductividad
        val cv3Model = ConMuestra(
            idSol,
            numToma.toInt(),
            bin.edtCon1.text.toString(),
            bin.edtCon2.text.toString(),
            bin.edtCon3.text.toString(),
            bin.txtConProm.text.toString(),
        )
        con.insertConMuestra(cv3Model)
        //Guardar Gasto
        val cv4Model = GastoMuestra(
            idSol,
            numToma.toInt(),
            bin.edtGasto1.text.toString(),
            bin.edtGasto2.text.toString(),
            bin.edtGasto3.text.toString(),
            bin.txtGastoProm.text.toString(),
        )
        con.insertGastoMuestra(cv4Model)
        onBackPressed()
    }
    fun LlenarSpinners(){

        val arrMateriaFloante = listOf<String>("Ausente","Presente")
        val arrColor = listOf<String>("Rojo","Verde","Amarrillo","Negro","Cafe")
        val arrOlor = listOf<String>("Si","No")

        val adaptador1 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrMateriaFloante)
        val adaptador2 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrColor)
        val adaptador3 = ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrOlor)

        bin.spnMateriaFlotante.adapter = adaptador1
        bin.spnColor.adapter = adaptador2
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
}

