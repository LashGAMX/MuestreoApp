package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.acama.muestreoapp.databinding.ActivityAguaEvidenciaBinding
import com.acama.muestreoapp.databinding.ActivityAguaGeneralesBinding

class AguaEvidenciaActivity : AppCompatActivity() {

    private lateinit var bin: ActivityAguaEvidenciaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaEvidenciaBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.imgRegresar.setOnClickListener(View.OnClickListener { v: View? ->
            onBackPressed()
        })
    }
}