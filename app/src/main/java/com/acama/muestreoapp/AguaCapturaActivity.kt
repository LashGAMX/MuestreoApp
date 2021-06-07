package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding

class AguaCapturaActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaCapturaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaCapturaBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.btnGenerales.setOnClickListener { generales() }
    }

    fun generales()
    {
        val intent = Intent(this,AguaGeneralesActivity::class.java)
        startActivity(intent)
    }
}