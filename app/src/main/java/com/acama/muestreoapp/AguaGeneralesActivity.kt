package com.acama.muestreoapp

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.acama.muestreoapp.databinding.ActivityAguaGeneralesBinding

class AguaGeneralesActivity : AppCompatActivity() {
    private lateinit var bin:ActivityAguaGeneralesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaGeneralesBinding.inflate(layoutInflater)
        setContentView(bin.root)


        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this,AguaCapturaActivity::class.java)
            startActivity(intent)
            finish()
        })

        bin.btnGuardar.setOnClickListener{ guardarDatos() }

        llenarSpinner()
    }
    fun guardarDatos() {
        val intent = Intent(this,AguaCapturaActivity::class.java)
        startActivity(intent)
    }

    fun llenarSpinner()
    {
        val termos = arrayOf("Termo 1", "Termo 2", "Termo 3", "Termo 4", "Termo 5")
        val adTermo = ArrayAdapter(
            this,
            R.layout.simple_spinner_item, termos
        )
        bin.spnTermo.adapter = adTermo

        val phTrazable = arrayOf("Select","7")

        val adPhTrazable = ArrayAdapter(
            this,
            R.layout.simple_spinner_item,phTrazable
        )
        bin.spnPhTrazable.adapter = adPhTrazable
        bin.spnPhTrazable2.adapter = adPhTrazable
        bin.spnPhTrazableCalidad.adapter = adPhTrazable
        bin.spnPhTrazableCalidad2.adapter = adPhTrazable

        val conductividad = arrayOf("1412","1315")

        val adpConductividad = ArrayAdapter(
            this,
            R.layout.simple_spinner_item,
            conductividad
        )
        bin.spnConductividad.adapter = adpConductividad

    }


}