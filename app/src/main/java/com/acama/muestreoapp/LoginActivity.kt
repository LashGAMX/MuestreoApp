package com.acama.muestreoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast

import com.acama.muestreoapp.databinding.ActivityLoginBinding
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class LoginActivity : AppCompatActivity() {

    private lateinit var bin: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bin.root)



        bin.btnEntrar.setOnClickListener {
             val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            //val queue = Volley.newRequestQueue(this)
            //val url = "https://dev.sistemaacama.com.mx/api/app/login"

            //val jsonArray = JSONArray()
           // jsonArray.put("isaac")
           // jsonArray.put("admin2421")
            

           // val jsonArrayRequest = JsonArrayRequest(Request.Method.POST, url, jsonArray,
             //   Response.Listener { response->
             //       Toast.makeText(applicationContext, "Bienvenido", Toast.LENGTH_LONG).show()
           // },Response.ErrorListener{ error ->
           //         Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
           // })
           // queue.add(jsonArrayRequest)
        }
    }
}
