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
import android.view.ViewManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.acama.muestreoapp.databinding.ActivityAguaCompuestosBinding
import com.acama.muestreoapp.models.CampoCompuesto
import kotlinx.android.synthetic.main.activity_agua_compuestos.spnAforo
import java.text.DecimalFormat
import kotlin.math.log


class AguaCompuestosActivity : AppCompatActivity() {
    private lateinit var bin: ActivityAguaCompuestosBinding
    private lateinit var con: DataBaseHandler
    private lateinit var folio:String
    private var idSol:Int = 0

    private var datosCompuesto: MutableList<String> =  mutableListOf()
    private var promedios : MutableList<Float> = mutableListOf()
    private var vmsi : ArrayList<Float> = ArrayList()

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
        // recuperar datos
        val qrCompuestos = "SELECT * FROM campo_compuesto WHERE Id_solicitud = '$idSol'"
        val compuestosModel = db.rawQuery(qrCompuestos, null)

        if (compuestosModel.moveToFirst()){
            do {
                datosCompuesto.add(compuestosModel.getString(0))
                datosCompuesto.add(compuestosModel.getString(1))
                datosCompuesto.add(compuestosModel.getString(2))
                datosCompuesto.add(compuestosModel.getString(3))
                datosCompuesto.add(compuestosModel.getString(4))
                datosCompuesto.add(compuestosModel.getString(5))
                datosCompuesto.add(compuestosModel.getString(6))
                datosCompuesto.add(compuestosModel.getString(7))
                datosCompuesto.add(compuestosModel.getString(8))
                datosCompuesto.add(compuestosModel.getString(9))
                datosCompuesto.add(compuestosModel.getString(10))
                datosCompuesto.add(compuestosModel.getString(11))
                datosCompuesto.add(compuestosModel.getString(12))

            } while (compuestosModel.moveToNext())
        }

        bin.btnObtenerVol.setOnClickListener(View.OnClickListener {
            try {
               getVolumenQi()
            } catch (e: Exception){
                Toast.makeText(this, "Error en algun Gasto", Toast.LENGTH_SHORT).show()
            }
        })
        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            DialogVolver()
        })

        bin.btnGuardarCompuestos.setOnClickListener {
            guardarDatos()
            onBackPressed()
        }
        llenarSpinner()
        MostrarDatos()

    }
    fun guardarDatos() {
//        val intent = Intent(this,AguaCapturaActivity::class.java)
//        startActivity(intent)
        val dbw: SQLiteDatabase = con.writableDatabase
        var cv = ContentValues()

        cv.put("Metodo_aforo",bin.spnAforo.selectedItem.toString())
        cv.put("Con_tratamiento",bin.spnConTratamiento.selectedItem.toString())
        cv.put("Tipo_tratamiento",bin.spnTipoTratamiento.selectedItem.toString())
        cv.put("Proc_muestreo",bin.edtProcedimiento.text.toString())
        cv.put("Observaciones",bin.edtObservaciones.text.toString())
        cv.put("Obser_solicitud","")
        cv.put("Ph_muestraComp",bin.edtPhCompuesto.text.toString())
        cv.put("Temp_muestraComp",bin.edtTempCompuesta.text.toString())
        cv.put("Volumen_calculado",bin.edtVolCalculado.text.toString())
        cv.put("Cloruros",bin.edtCloruros.selectedItem.toString())
        cv.put("Cloro",bin.edtCloroCompuesto.text.toString())

        dbw.update("campo_compuesto", cv, "Id_solicitud = "+idSol, null)

         //onBackPressed()
        //guardar datos en table metodo Update

    }
    fun getVolumenQi() {
        bin.edtVime.setText("Gasto1: 1.5 \n Gasto 2: 1.6")
        val db: SQLiteDatabase = con.readableDatabase
        var suma = 0.0
        var cont = 0
        var prom = 0.0
        var aux = ""
        var vime = 0.0
        var volumen = ""
        val queryGasto = "SELECT * FROM gasto_muestra WHERE Id_solicitud = '$idSol'"
        var toma = 0
        val gasto = db.rawQuery(queryGasto, null)
        volumen = bin.edtVolCalculado.text.toString()
        if (gasto.moveToFirst()){
            do {
                toma =  gasto.getString(2).toInt()
                val queryCanceladas =  "SELECT * FROM canceladas WHERE Id_solicitud = '$idSol' AND " +
                        "Muestra = '$toma'"
                val canceladas =  db.rawQuery(queryCanceladas, null)
                if (canceladas.moveToFirst()){

                } else {
                    suma = suma + gasto.getString(6).toFloat()
                    cont++
                }
            } while (gasto.moveToNext())
            prom = suma / cont
        }
        cont = 1
        if (gasto.moveToFirst()){
            do {
                toma =  gasto.getString(2).toInt()
                val queryCanceladas =  "SELECT * FROM canceladas WHERE Id_solicitud = '$idSol' AND " +
                        "Muestra = '$toma'"
                val canceladas =  db.rawQuery(queryCanceladas, null)
                if (canceladas.moveToFirst()){
                     aux += "gasto " + cont + ": "+"CANCELADO\n"

                } else {
                    vime = (gasto.getString(6).toDouble() / suma) * volumen.toFloat()
                    aux += "gasto " + cont +  ": " + roundToTwoDecimals(vime) + "\n"
                }
                cont++
            } while (gasto.moveToNext())
        }
        bin.edtVime.setText(aux)

    }

    fun roundToTwoDecimals(number: Double): Double {
        val decimalFormat = DecimalFormat("#.00")
        return decimalFormat.format(number).toDouble()
    }

    fun getExtras(){
        val bundle = intent.extras
        val fol = bundle?.get("folio")
        folio = fol.toString()
        Log.d("Folio",folio)
    }
    fun
            llenarSpinner() {
        val aforos : MutableList<String> = ArrayList()
        val tratamiento : MutableList<String> = ArrayList()
        val conTratamiento : MutableList<String> = ArrayList()
        val clorurosArray = listOf<String>("Seleciona uno","< 500","= 500","= 1000","> 1000")
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
        val adaptador3 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clorurosArray)

        bin.spnTipoTratamiento.adapter = adpTratamiento
        bin.spnAforo.adapter = adpAforo
        bin.spnConTratamiento.adapter = adpConTratamiento
        bin.edtCloruros.adapter = adaptador3


    }
    fun MostrarDatos(){
        val db: SQLiteDatabase = con.readableDatabase
        val aforos : MutableList<String> = ArrayList()
        val tratamiento : MutableList<String> = ArrayList()
        val conTratamiento : MutableList<String> = ArrayList()
        val clorurosArray = listOf<String>("< 500","= 500","= 1000","> 1000")

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
        val adaptador3 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clorurosArray)


        bin.spnAforo.adapter = adpAforo
        val aforo : String = datosCompuesto[2]
        val conTra : String = datosCompuesto[3]
        val tipoTra : String = datosCompuesto[4]
        val cloru : String = datosCompuesto[11]
        val spinPosition : Int = adpAforo.getPosition(aforo)
        val spinPosition2 : Int = adpConTratamiento.getPosition(conTra)
        val spinPosition3 : Int = adpTratamiento.getPosition(tipoTra)
        val clorurosPosition : Int = adaptador3.getPosition(cloru)
        bin.spnAforo.setSelection(spinPosition)
        bin.spnConTratamiento.setSelection(spinPosition2)
        bin.spnTipoTratamiento.setSelection(spinPosition3)
        bin.edtCloruros.setSelection(clorurosPosition)

        bin.edtProcedimiento.setText(datosCompuesto[5])
        bin.edtObservaciones.setText(datosCompuesto[6])

        bin.edtVolCalculado.setText(datosCompuesto[10])
        bin.edtPhCompuesto.setText(datosCompuesto[8])
        bin.edtTempCompuesta.setText(datosCompuesto[9])
        bin.edtCloroCompuesto.setText(datosCompuesto[12])
       // bin.edtCloruros.setSelection(datosCompuesto[11])

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