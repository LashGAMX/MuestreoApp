package com.acama.muestreoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.acama.muestreoapp.databinding.ActivityMuestraSimpleBinding

class MuestraSimpleActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityMuestraSimpleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bin = ActivityMuestraSimpleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bin.root)

        val materiaFlotante = bin.spnMateriaFlotante

        ArrayAdapter.createFromResource(
            this,
            R.array.materia_flotante,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            materiaFlotante.adapter = adapter
        }
    }
}