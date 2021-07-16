package com.acama.muestreoapp

import android.app.AlertDialog
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.acama.muestreoapp.agua.MuestraSimple
import com.acama.muestreoapp.agua.MuestraSimpleAdapter
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding
import com.acama.muestreoapp.databinding.ActivityAguaDatosMuestreosBinding
import com.acama.muestreoapp.preference.UserApplication

class AguaDatosMuestreosActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaDatosMuestreosBinding
    private  lateinit var folio:String
    private  lateinit var con: DataBaseHandler
    var muestraSimple = listOf(
        MuestraSimple(1),
        MuestraSimple(2)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaDatosMuestreosBinding.inflate(layoutInflater)
        setContentView(bin.root)

        getExtras()


        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            DialogVolver()
        })

        initRecycler()
    }

    fun getNumTomas(){

    }

    fun initRecycler(){
        bin.rvMuestraSimple.layoutManager = LinearLayoutManager(this)
        val adapter = MuestraSimpleAdapter(muestraSimple)
        bin.rvMuestraSimple.adapter = adapter
    }

    fun getExtras(){
        val bundle = intent.extras
        val fol = bundle?.get("folio")
        folio = fol.toString()
    }

    fun DialogVolver(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cuidado")
        builder.setMessage("Los datos capturados se perderan ¿Seguro que quieres salir?")

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.yes, Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            Toast.makeText(applicationContext,
                android.R.string.no, Toast.LENGTH_SHORT).show()
        }
        builder.show()

    }
}