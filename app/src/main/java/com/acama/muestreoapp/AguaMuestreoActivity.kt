package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding
import com.acama.muestreoapp.databinding.ActivityAguaMuestreoBinding
import com.acama.muestreoapp.databinding.ActivityMuestreosBinding

class AguaMuestreoActivity : AppCompatActivity() {

    private lateinit var bin: ActivityAguaMuestreoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaMuestreoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_agua_muestreo)

        bin.btnPH.setOnClickListener {
            val intent = Intent(this, AguaMuestreoPhActivity::class.java)
            startActivity(intent)
        }
        bin.btnTemperatura.setOnClickListener {
            val intent = Intent(this, AguaMuestreoTemperaturaActivity::class.java)
            startActivity(intent)
        }
        bin.btnConductividad.setOnClickListener {
            val intent = Intent(this, AguaMuestreoPhActivity::class.java)
            startActivity(intent)
        }
        bin.btnGasto.setOnClickListener {
            val intent = Intent(this, AguaMuestreoPhActivity::class.java)
            startActivity(intent)
        }


    }

}