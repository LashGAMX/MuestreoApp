package com.acama.muestreoapp

import android.R
import android.app.AlertDialog
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityMuestraSimpleBinding

class MuestraSimpleActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityMuestraSimpleBinding
    private lateinit var folio:String
    private lateinit var idSol:Int
    private lateinit var con: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        bin = ActivityMuestraSimpleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bin.root)
        con = DataBaseHandler(this)
        val db: SQLiteDatabase = con.readableDatabase

        folio = intent.getStringExtra("folio").toString()
        val qrSolGenModel = "SELECT * FROM solicitud_generadas WHERE Folio_servicio = '$folio'"
        val solGenModel = db.rawQuery(qrSolGenModel, null)
        if (solGenModel.moveToFirst()) {
            do {
                idSol = solGenModel.getInt(0)
            } while (solGenModel.moveToNext())
        }

        bin.btnRegresar.setOnClickListener{
            DialogVolver()
        }

        //Boton guardar datos
        bin.btnGuardar.setOnClickListener{

        }

       LlenarSpinners()

    }
    fun guardarDatos(){
        val dbw: SQLiteDatabase = con.writableDatabase

        //Guardar Ph
        val cv = ContentValues()
        cv.put("Materia",bin.spnMateriaFlotante.selectedItem.toString())
        cv.put("Olor",bin.spnOlor.selectedItem.toString())
        cv.put("Color",bin.spnColor.selectedItem.toString())
        cv.put("Ph1",bin.edtPh1.text.toString())
        cv.put("Ph2",bin.edtPh1.text.toString())
        cv.put("Ph3",bin.edtPh1.text.toString())
        cv.put("Promedio",bin..text.toString())
        cv.put("Fecha",bin.edtPh1.text.toString())
        dbw.update("ph_muestra",cv,"Id_solicitud = "+idSol,null)
        //Guarda Temp
        val cv2 = ContentValues()
        cv.put("Ph1",bin.edtPh1.text.toString())
        cv.put("Ph2",bin.edtPh1.text.toString())
        cv.put("Ph3",bin.edtPh1.text.toString())
        dbw.update("temperatura_muestra",cv,"Id_solicitud = "+idSol,null)
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