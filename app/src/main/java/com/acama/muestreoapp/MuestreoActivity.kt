package com.acama.muestreoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acama.muestreoapp.databinding.ActivityMenuBinding
import com.acama.muestreoapp.databinding.ActivityMuestreoBinding
import com.acama.muestreoapp.databinding.ActivityMuestreosBinding

class MuestreoActivity : AppCompatActivity() {

    private lateinit var bin: ActivityMuestreoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bin = ActivityMuestreoBinding.inflate(layoutInflater)
        setContentView(bin.root)
    }
}