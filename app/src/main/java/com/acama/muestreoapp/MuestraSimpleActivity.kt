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
import com.acama.muestreoapp.models.ConMuestra
import com.acama.muestreoapp.models.GastoMuestra
import com.acama.muestreoapp.models.PhMuestra
import com.acama.muestreoapp.models.TempMuestra

class MuestraSimpleActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityMuestraSimpleBinding
    private lateinit var folio:String
    private var idSol:Int = 0
    private lateinit var numToma:String
    private lateinit var con: DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        bin = ActivityMuestraSimpleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bin.root)
        con = DataBaseHandler(this)
        val db: SQLiteDatabase = con.readableDatabase

        folio = intent.getStringExtra("folio").toString()
        numToma = intent.getStringExtra("numToma").toString()

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
            //guardarDatos()
        }

       LlenarSpinners()

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
            "",
            ""
        )
        con.insertPhMuestra(cvModel)
    // Guardar TempMuestra
        val cv2Model = TempMuestra(
            idSol,
            numToma.toInt(),
            bin.edtTemp1.text.toString(),
            bin.edtTemp2.text.toString(),
            bin.edtTemp3.text.toString(),
            "",
        )
        con.insertTempMuestra(cv2Model)
        //Guardar Conductividad
        val cv3Model = ConMuestra(
            idSol,
            numToma.toInt(),
            bin.edtCon1.text.toString(),
            bin.edtCon2.text.toString(),
            bin.edtCon3.text.toString(),
            "",
        )
        con.insertConMuestra(cv3Model)
        //Guardar Gasto
        val cv4Model = GastoMuestra(
            idSol,
            numToma.toInt(),
            bin.edtCon1.text.toString(),
            bin.edtCon2.text.toString(),
            bin.edtCon3.text.toString(),
            "",
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

