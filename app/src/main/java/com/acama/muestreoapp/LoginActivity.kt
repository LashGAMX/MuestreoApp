package com.acama.muestreoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast


import com.acama.muestreoapp.databinding.ActivityLoginBinding
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var bin: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bin.root)
        bin.btnEntrar.setOnClickListener {

        }
    }
    private fun addUser() {
        //getting the record values

        val url = "https://dev.sistemaacama.com.mx/api/app/user"

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("data"), Toast.LENGTH_LONG).show()
                    val data = JSONObject(obj["data"].toString())
                    //bin.txtPeticion.text = data.get("User").toString()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    //bin.txtPeticion.text = "Error en la peticion"
                }

            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("user", "Admin")
                params.put("pass", "Admin1234")
                return params
            }
        }

        //adding request to queue
        //VolleySingleton.instance?.addToRequestQueue(stringRequest)
        //MySingleton.getInstance(this).addToRequestQueue(stringRequest)
    }
}
