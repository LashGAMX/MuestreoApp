package com.acama.muestreoapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding
import com.acama.muestreoapp.databinding.ActivityAguaDatosMuestreosBinding

class AguaDatosMuestreosActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaDatosMuestreosBinding
    private  lateinit var folio:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaDatosMuestreosBinding.inflate(layoutInflater)
        setContentView(bin.root)

        getExtras()

        bin.btnMS1.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this, MuestraSimpleActivity::class.java)
            startActivity(intent)
        })
        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            DialogVolver()
        })

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