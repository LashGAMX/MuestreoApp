package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.StartElementListener
import android.widget.Button
import android.widget.Toast
import com.acama.muestreoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bin: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bin = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bin.root)

    }
}