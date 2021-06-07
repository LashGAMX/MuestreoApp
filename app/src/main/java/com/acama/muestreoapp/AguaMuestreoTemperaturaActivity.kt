package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acama.muestreoapp.databinding.ActivityAguaMuestreoTemperaturaBinding
import com.acama.muestreoapp.databinding.ActivityMuestraSimpleBinding

class AguaMuestreoTemperaturaActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityAguaMuestreoTemperaturaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaMuestreoTemperaturaBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.btnMS1.setOnClickListener {
            val intent = Intent(this, MuestraSimpleTemperaturaActivity::class.java)
            startActivity(intent)
        }
    }
}