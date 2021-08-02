package com.acama.muestreoapp

import android.R
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityMuestraSimpleBinding

class MuestraSimpleActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityMuestraSimpleBinding
    private lateinit var folio:String

    override fun onCreate(savedInstanceState: Bundle?) {
        bin = ActivityMuestraSimpleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bin.root)

        folio = intent.getStringExtra("folio").toString()

        //Toast.makeText(this,"Folio"+folio,Toast.LENGTH_SHORT).show()

        bin.btnRegresar.setOnClickListener{
            DialogVolver()
        }

        //Boton guardar datos
        bin.btnGuardar.setOnClickListener{

        }

       LlenarSpinners()

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