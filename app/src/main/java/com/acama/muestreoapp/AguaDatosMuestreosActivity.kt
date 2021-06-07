package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.acama.muestreoapp.databinding.ActivityAguaCapturaBinding
import com.acama.muestreoapp.databinding.ActivityAguaDatosMuestreosBinding

class AguaDatosMuestreosActivity : AppCompatActivity() {
    private  lateinit var bin: ActivityAguaDatosMuestreosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityAguaDatosMuestreosBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.btnMS1.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(this, MuestraSimpleActivity::class.java)
            startActivity(intent)
        })

    }
}