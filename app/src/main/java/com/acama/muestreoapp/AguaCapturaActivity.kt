package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding

class AguaCapturaActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaCapturaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaCapturaBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.btnGenerales.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this, AguaGeneralesActivity::class.java)
            startActivity(intent)
        })

        bin.btnCompuesto.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this,AguaCompuestosActivity::class.java)
            startActivity(intent)
        })
    }

}