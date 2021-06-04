package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bin: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bin.root)

        bin.btnEntrar.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)

        }
    }
}
