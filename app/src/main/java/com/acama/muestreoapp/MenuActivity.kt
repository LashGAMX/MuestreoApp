package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityMenuBinding
import com.acama.muestreoapp.databinding.ActivityMuestreosBinding
import com.acama.muestreoapp.preference.UserApplication

class MenuActivity : AppCompatActivity() {

    private lateinit var bin: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(bin.root)

        var user = UserApplication.prefs.getMuestreador()
        Toast.makeText(this,"Bienvenido $user" ,Toast.LENGTH_LONG).show()

        bin.cardAgua.setOnClickListener(View.OnClickListener { v: View? ->

            val intent = Intent(this, ListaAguaActivity::class.java)
            startActivity(intent)
        })
    }
}