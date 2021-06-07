package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.acama.muestreoapp.databinding.ActivityMenuBinding
import com.acama.muestreoapp.databinding.ActivityMuestreosBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var bin: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.cardAgua.setOnClickListener(View.OnClickListener { v: View? ->

            val intent = Intent(this, ListaAguaActivity::class.java)
            startActivity(intent)
        })
    }
}