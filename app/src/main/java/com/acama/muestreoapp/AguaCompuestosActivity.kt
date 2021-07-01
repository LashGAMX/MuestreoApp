package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.acama.muestreoapp.databinding.ActivityAguaCompuestosBinding

class AguaCompuestosActivity : AppCompatActivity() {
    private lateinit var bin: ActivityAguaCompuestosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaCompuestosBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            onBackPressed()
        })

        bin.btnGuardar.setOnClickListener { guardarDatos() }
        llenarSpinner()
    }
    fun guardarDatos() {
        val intent = Intent(this,AguaCapturaActivity::class.java)
        startActivity(intent)
    }
    fun llenarSpinner() {
        val aforos = arrayOf("Aforo 1","Aforo 2","Aforo 3")
        val adpAforo = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            aforos
        )
        bin.spnAforo.adapter = adpAforo

        val conTratamiento = arrayOf("Tratamiento 1","Tratamiento 2","Tratamiento 3")
        val adpConTratamiento = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            conTratamiento
        )
        bin.spnConTratamiento.adapter = adpConTratamiento
        bin.spnTipoTratamiento.adapter = adpConTratamiento

    }

}