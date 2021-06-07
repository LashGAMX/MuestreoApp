package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding
import com.acama.muestreoapp.databinding.ActivityAguaMuestreoPhBinding

class AguaMuestreoPhActivity : AppCompatActivity() {

    private  lateinit var bin: ActivityAguaMuestreoPhBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaMuestreoPhBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_agua_muestreo_ph)

        bin.btnMS1.setOnClickListener {
            val intent = Intent(this, MuestraSimpleActivity::class.java)
            startActivity(intent)
        }

    }
}